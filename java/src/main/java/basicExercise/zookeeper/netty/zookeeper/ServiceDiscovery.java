package basicExercise.zookeeper.netty.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jinyan on 6/19/17.
 */
public class ServiceDiscovery {
    private CountDownLatch latch = new CountDownLatch(1);
    private volatile List<String> dataList = new ArrayList<String>();
    private String registryAddress;


    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);
            } else {
                //随机获取其中的一个
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
            }
        }
        return data;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            //format host1:port1,host2:port2,host3:port3
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    //zookeeper处于同步连通的状态时
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {//获得注册路径下的子节点
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);//监控子节点发生变化的事件，
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            this.dataList = dataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
