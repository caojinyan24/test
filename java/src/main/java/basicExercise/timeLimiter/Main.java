package basicExercise.timeLimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程限流
 * Created by jinyancao on 3/3/17.
 */
public class Main {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static RateLimiter rateLimiter = RateLimiter.create(3.0);

    private static Integer initNum = 0;

    public static void main(String[] args) throws Exception {
        Main object = new Main();
        object.start();
//        for (Integer i = 0; i < 1000; i++) {
//            object.executeTimeLimiter();
//            System.out.println("execute complete:" + i + "   " + new Date().getTime());
//
//        }


        for (Integer i = 0; i < 1000; i++) {
            object.excuteRateLimiter();
            System.out.println("execute complete:" + i + "   " + (new Date().getTime() - 1488510734857L));

        }

    }

    /**
     * TimeLimiter.callWithTimeout:保证线程在指定时间内结束。若在指定时间内结束，流程正常继续；否则跑出TimeOut异常
     *
     * @throws Exception
     */

    public void executeTimeLimiter() throws Exception {
        TimeLimiter timeLimiter = new SimpleTimeLimiter(executorService);
        timeLimiter.callWithTimeout(() -> {
            System.out.println(initNum++);
            Thread.sleep((initNum % 10) * 100L);
            return true;
        }, 400L, TimeUnit.MILLISECONDS, false);//修改400为200,300,400观察效果
    }

    public void excuteRateLimiter() throws Exception {
        rateLimiter.acquire();
        executorService.submit((Runnable) () -> {
            try {
                Thread.sleep((initNum++ % 10) * 100L);
                System.out.println(initNum+ "   " + (new Date().getTime() - 1488510734857L));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private class ShutdownHook extends Thread {
        @Override
        public void run() {
            executorService.shutdown();
        }
    }

    public void start() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

    }
}

