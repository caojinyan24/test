package basicExercise.thrift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * {@link QFrameBuffer}负责Socket通信数据读写
 *
 * @author hanhan.zhang
 */
public class QFrameBuffer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QFrameBuffer.class);

    // Socket读取状态
    private QFrameBufferState _state = QFrameBufferState.READING_FRAME_SIZE;

    // 缓冲区[socket read/write]
    private ByteBuffer _buffer;

    // QFrameBuffer关联的SocketChannel
    private SocketChannel _channel;

    // SocketChannel注册的SelectorKey
    private SelectionKey _selectionKey;

    // 服务端数据响应
    private QMemoryInputBuffer _outputBuffer;

    // 客户端发送的数据
    private QMemoryInputBuffer _inputBuffer;

    public QFrameBuffer(SocketChannel _channel, SelectionKey _key) {
        this._channel = _channel;
        this._selectionKey = _key;

        // QFrameBuffer初始状态读取Socket数据包包头, 故Buffer的容量设置为4个字节
        this._buffer = ByteBuffer.allocate(4);
        this._inputBuffer = new QMemoryInputBuffer();
        this._outputBuffer = new QMemoryInputBuffer();
    }

    public void cleanupSelectionKey(SelectionKey key) {
        QFrameBuffer frameBuffer = (QFrameBuffer) key.attachment();
        if (frameBuffer != null) {
            frameBuffer.close();
        }
        key.cancel();
    }

    // Socket Read
    private boolean internalRead() {
        try {
            if (_channel.read(_buffer) < 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warn("Got an IOException in internalRead!", e);
            return false;
        }
    }

    public boolean doRead() {
        // 读取Socket数据包包头[_buffer初始化长度为4个字节]
        if (_state == QFrameBufferState.READING_FRAME_SIZE) {
            if (!internalRead()) {
                return false;
            }

            if (_buffer.remaining() == 0) {
                _buffer.flip();
                // 已读取4字节包头长度
                int frameSize = _buffer.getInt();

                // 重新分配Buffer
                _buffer = ByteBuffer.allocate(frameSize + 4);
                _buffer.putInt(frameSize);
                // 更改QFrameBufferState状态[Socket数据包读取完成,开始读取Socket包体]
                _state = QFrameBufferState.READING_FRAME;
            } else {
                // socket没有数据传输
                return true;
            }
        }

        // 读取Socket数据包包体
        if (_state == QFrameBufferState.READING_FRAME) {
            if (!internalRead()) {
                return false;
            }

            //
            if (_buffer.remaining() == 0) {
                _selectionKey.interestOps(0);
                _state = QFrameBufferState.READ_FRAME_COMPLETE;
            }
            return true;
        }

        LOGGER.error("Read was called but state is invalid ({})", _state);
        return false;
    }

    // SocketChannel数据写完,准备接收客户端数据
    private void prepareRead() {
        _selectionKey.interestOps(SelectionKey.OP_READ);
        _buffer = ByteBuffer.allocate(4);
        _state = QFrameBufferState.READING_FRAME_SIZE;
    }

    public boolean doWrite() {
        if (_state == QFrameBufferState.WRITING) {
            try {
                if (_channel.write(_buffer) < 0) {
                    return false;
                }
            } catch (IOException e) {
                LOGGER.warn("Got an IOException during write!", e);
                return false;
            }

            // 数据写结束
            if (_buffer.remaining() == 0) {
                prepareRead();
            }
            return true;
        }
        return false;
    }

    private void close() {
        try {
            _channel.close();
            _selectionKey.cancel();
        } catch (Exception e) {
            // ignore
        }
    }

    // Socket数据包是否完全读取
    public boolean isFrameFullyRead() {
        return _state == QFrameBufferState.READ_FRAME_COMPLETE;
    }

    /**
     * 根据FrameBuffer当前状态修改SelectionKey关注事件
     */
    private void changeSelectInterests() {
        if (_state == QFrameBufferState.AWAITING_REGISTER_WRITE) {
            _selectionKey.interestOps(SelectionKey.OP_WRITE);
            _state = QFrameBufferState.WRITING;
        } else if (_state == QFrameBufferState.AWAITING_REGISTER_READ) {
            prepareRead();
        } else if (_state == QFrameBufferState.AWAITING_CLOSE) {
            close();
        } else {
            LOGGER.error("changeSelectInterest was called, but state is invalid ({})", _state);
        }

        // 唤醒阻塞的Selector
        _selectionKey.selector().wakeup();
    }

    /**
     * 服务端完成业务逻辑并将结果返回到客户端
     */
    private void readyResponse() {
        if (_outputBuffer.getBytesRemainingInBuffer() == 0) {
            // 服务端无数据响应, 则直接更改FrameBuffer状态为AWAITING_REGISTER_READ
            // Note:
            //   此处Buffer设置null, 在changeSelectInterests中将SelectionKey注册OP_READ事件并对Buffer初始化
            _state = QFrameBufferState.AWAITING_REGISTER_READ;
            _buffer = null;
        } else {
            // 服务端响应, 则更改FrameBuffer状态AWAITING_REGISTER_WRITE
            _state = QFrameBufferState.AWAITING_REGISTER_WRITE;
            _buffer = ByteBuffer.wrap(_outputBuffer.getBuffer(), _outputBuffer.getBufferPosition(), _outputBuffer.getBufferLength());
        }
        // 更改SelectionKey状态
        changeSelectInterests();
    }

    // SocketChannel数据读取完成后,处理业务
    public void invoke() {
        try {
            // 处理业务逻辑
            _inputBuffer.reset(_buffer.array());
            System.out.println("server receive : " + new String(_inputBuffer.getBuffer()));
            // 模拟输出数据
            _outputBuffer.reset("OK".getBytes());
            readyResponse();
            return;
        } catch (Exception e) {
            LOGGER.warn("Exception while invoking!", e);
        } catch (Throwable t) {
            LOGGER.error("Unexpected throwable while invoking!", t);
        }

        // 发生异常, FrameBuffer状态更改为AWAITING_CLOSE
        _state = QFrameBufferState.AWAITING_CLOSE;
        changeSelectInterests();
    }

    private enum QFrameBufferState {
        // 读Frame消息头,实际是4字节表示Frame长度(解决Socket粘包/拆包问题)
        READING_FRAME_SIZE,
        // 读Frame消息体
        READING_FRAME,
        // 读满包(Socket数据包读取完成)
        READ_FRAME_COMPLETE,

        // 等待注册写
        AWAITING_REGISTER_WRITE,
        // 写半包
        WRITING,
        // 等待注册读
        AWAITING_REGISTER_READ,
        // 等待关闭
        AWAITING_CLOSE
    }
}
