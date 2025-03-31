package lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;

public class RedisLock {
    private Jedis jedis;
    private String lockKey;
    private String lockValue;
    private int expireTime; // 锁的过期时间（秒）

    public RedisLock(Jedis jedis, String lockKey, int expireTime) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.expireTime = expireTime;
        this.lockValue = UUID.randomUUID().toString(); // 唯一标识
    }

    /**
     * 尝试获取锁
     *
     * @return 是否成功获取锁
     */
    public boolean acquireLock() {
        SetParams setParams = SetParams.setParams().ex(expireTime);
        setParams.nx();
        String result = jedis.set(lockKey, lockValue, setParams);
        return "OK".equals(result);
    }

    /**
     * 释放锁
     *
     * @return 是否成功释放锁
     */
    public boolean releaseLock() {
        // Lua 脚本保证解锁的原子性
        String luaScript =
                "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "   return redis.call('del', KEYS[1]) " +
                        "else " +
                        "   return 0 " +
                        "end";
        Object result = jedis.eval(luaScript, 1, lockKey, lockValue);
        return "1".equals(result.toString());
    }

    public static void main(String[] args) {
        // 创建 Jedis 客户端
        Jedis jedis = new Jedis("localhost", 6379);

        // 创建 RedisLock 实例
        RedisLock lock = new RedisLock(jedis, "my_distributed_lock", 10);

        // 尝试获取锁
        if (lock.acquireLock()) {
            System.out.println("成功获取锁");
            try {
                // 执行临界区代码
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                if (lock.releaseLock()) {
                    System.out.println("成功释放锁");
                } else {
                    System.out.println("释放锁失败");
                }
            }
        } else {
            System.out.println("获取锁失败");
        }

        // 关闭 Jedis 客户端
        jedis.close();
    }
}
