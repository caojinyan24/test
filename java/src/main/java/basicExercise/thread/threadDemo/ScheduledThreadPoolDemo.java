package basicExercise.thread.threadDemo;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 调度线程
 * Created by jinyan.cao on 2017/4/2.
 */
public class ScheduledThreadPoolDemo {

    private ScheduledExecutorService executerService = Executors.newScheduledThreadPool(2);

    public void executeInDelay() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 20; i++) {
            System.out.println("execute begin:" + i + "   " + Thread.currentThread().getName());
            executerService.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + "    " + DateFormat.getDateTimeInstance().format(new Date()));
                return true;
            }, 1L, TimeUnit.SECONDS);
            System.out.println("execute end:" + "   " + Thread.currentThread().getName());
        }
        destroy();
    }

    public void executeAtFixRate() throws Exception {
//        for (int i = 0; i < 5; i++) {
//        System.out.println("execute begin:" + i + "   " + Thread.currentThread().getName());
        executerService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "    " + DateFormat.getDateTimeInstance().format(new Date()));
        }, 0L, 1000L, TimeUnit.MILLISECONDS);
        System.out.println("execute end:" + "   " + Thread.currentThread().getName());
//        }
        destroy();
    }

    private void destroy() throws InterruptedException {
        if (executerService.awaitTermination(30, TimeUnit.SECONDS)) {
            executerService.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolDemo test = new ScheduledThreadPoolDemo();
//        test.executeInDelay();
        test.executeAtFixRate();
    }
}
