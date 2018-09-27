package basicExercise.java;

/**
 * Created by jinyan.cao on 2017/10/10 17:39.
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                ThreadLocal<Long> local = new ThreadLocal<>();
//                local.set(System.currentTimeMillis());
//                System.out.println(local.get());
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
            ThreadLocal<Long> local = new ThreadLocal<>();
            local.set(System.currentTimeMillis());
            System.out.println(local.get());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
