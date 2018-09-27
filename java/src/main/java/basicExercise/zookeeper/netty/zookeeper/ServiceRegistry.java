package basicExercise.zookeeper.netty.zookeeper;

import io.netty.util.Constant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jinyan on 6/19/17.
 */
public class ServiceRegistry {
    private CountDownLatch latch = new CountDownLatch(1);
    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    //注册到zk中，其中data为服务端的 ip:port
    public void register(String data) {//注册完成之后，创建一个新的zk节点
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, 10000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create("zk_node", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("create zookeeper node path:"+path+" data:"+data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
