package basicExercise.future;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by jinyancao on 2/10/17.
 */
public class FutureProcessor {
    private static FutureTask<Boolean> future = null;

    public void send() {
        future = new FutureTask<Boolean>(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                System.out.println("callback work");
                Thread.sleep(1900);
                return true;
            }
        });
        System.out.println("run before" + new Date().getTime() / 1000);
        future.run();
        System.out.println("run after" + new Date().getTime() / 1000);
        System.out.println("get data before" + new Date().getTime() / 1000);
    }

    public void get() {
        Boolean result = null;
        System.out.println("get data begin" + new Date().getTime() / 1000);

        try {
//            result = future.get(1000, TimeUnit.MILLISECONDS);
            result = future.get();
            if (result != null && result == Boolean.TRUE) {
                System.out.println("get data after success" + new Date().getTime() / 1000);
                System.out.println("complete");
            } else {
                System.out.println("get data after fail" + new Date().getTime() / 1000);
                System.out.println("fail");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
