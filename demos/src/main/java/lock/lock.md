锁相关
   Java 锁的分类和原理
   synchronized 底层实现
   ReentrantLock 实现原理
   读写锁 ReadWriteLock
   分布式锁实现(Redis/Zookeeper)
   死锁问题
   产生条件
   排查方法(jstack等)
   预防措施


锁与并发问题详解
# Java锁分类与实现原理
1.1 Synchronized
   java
   // 对象锁
   public void method() {
   synchronized(this) {
   // 临界区
   }
   }

// 类锁
public static synchronized void method() {
// 临界区
}
实现原理：Monitor机制
锁升级过程：无锁 -> 偏向锁 -> 轻量级锁 -> 重量级锁
优化：锁消除、锁粗化
1.2 ReentrantLock
java
private ReentrantLock lock = new ReentrantLock();

public void method() {
lock.lock();
try {
// 临界区
} finally {
lock.unlock();
}
}
特性：可重入、可中断、可设置超时、公平/非公平
原理：AQS (AbstractQueuedSynchronizer)
1.3 读写锁
java
private ReadWriteLock rwLock = new ReentrantReadWriteLock();
private Lock readLock = rwLock.readLock();
private Lock writeLock = rwLock.writeLock();
2. 死锁问题
   2.1 死锁示例
   java
   public class DeadLockDemo {
   private static Object lockA = new Object();
   private static Object lockB = new Object();

   public static void main(String[] args) {
   Thread t1 = new Thread(() -> {
   synchronized (lockA) {
   sleep(1000);
   synchronized (lockB) {
   System.out.println("Thread 1");
   }
   }
   });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                sleep(1000);
                synchronized (lockA) {
                    System.out.println("Thread 2");
                }
            }
        });
   }
   }
   2.2 死锁排查
   sh
# 使用jstack查看线程堆栈
jstack <pid>

# 使用jconsole图形化查看
jconsole

# 分布式锁实现
## Redis分布式锁
   java
   public boolean lock(String key, String value, long timeout) {
   return redis.setnx(key, value, timeout);
   }

public boolean unlock(String key, String value) {
String script = "if redis.call('get',KEYS[1]) == ARGV[1] then" +
"   return redis.call('del',KEYS[1]) " +
"else" +
"   return 0 " +
"end";
return redis.eval(script, keys, values);
}
##  Zookeeper分布式锁
Zookeeper 可以实现分布式锁的原因主要基于其以下特性：
1. 强一致性
   Zookeeper 保证了数据的强一致性，所有客户端对同一个节点的操作是线性化的。这意味着多个客户端对锁节点的创建、删除等操作是有序的，避免了竞争条件。
2. 临时节点
   Zookeeper 支持临时节点（EPHEMERAL），当客户端断开连接时，临时节点会自动删除。这可以防止因客户端故障导致锁无法释放的问题。
3. 顺序节点
   Zookeeper 支持顺序节点（EPHEMERAL_SEQUENTIAL），可以用来实现公平锁。多个客户端创建顺序节点后，锁的持有者是最小序号的节点，其他客户端可以监听锁节点的变化。
4. Watcher机制
   Zookeeper 提供了 Watcher 机制，客户端可以监听节点的变化。当锁节点被删除时，其他等待锁的客户端会收到通知，从而尝试获取锁。
5. 分布式架构
   Zookeeper 是一个分布式协调服务，支持多节点部署，具有高可用性和容错能力，适合分布式环境下的锁管理。
   分布式锁实现流程
   获取锁：
   客户端尝试创建一个临时节点（如 /lock）。
   如果创建成功，表示获取锁。
   如果创建失败，表示锁已被其他客户端持有。
   释放锁：
   持有锁的客户端删除锁节点。
   如果客户端故障，临时节点会自动删除，锁自动释放。
   等待锁：
   如果锁节点已存在，客户端可以监听该节点的删除事件。
   当锁节点被删除时，客户端收到通知并尝试重新获取锁。
