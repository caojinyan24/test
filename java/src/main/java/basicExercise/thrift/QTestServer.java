package basicExercise.thrift;


import java.io.IOException;

/**
 * Test Server
 *
 * @author hanhan.zhang
 */
public class QTestServer {

    public static void main(String[] args) throws IOException {
        QNioServer.QServerArgs serverArgs = new QNioServer.QServerArgs();
        serverArgs.setSelectorThreadNumber(4);
        serverArgs.setQueueSizePerSelectorThread(10);
        serverArgs.setMinWorkerThread(5);
        serverArgs.setMaxWorkerThread(10);
        serverArgs.setHost("127.0.0.1");
        serverArgs.setPort(6721);

        QNioServer server = new QNioServer(serverArgs);

        server.serve();
    }

}
