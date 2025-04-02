package lock;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperLock {
    private final String lockPath = "/lock";
    private ZooKeeper zk;

    public boolean lock() {
        try {
            zk.create(lockPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ... existing code ...
    public boolean unlock() {
        try {
            zk.delete(lockPath, -1); // -1 表示忽略版本号
            return true;
        } catch (Exception e) {
            return false;
        }
    }
// ... existing code ...

}