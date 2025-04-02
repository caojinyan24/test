package basicExercise.future;

import com.google.common.util.concurrent.*;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by jinyancao on 2/10/17.
 */
public class GuavaFutureProcessor {
    private ListenableFuture future;
    ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public void put() {
        future = service.submit(new Callable() {
            public Boolean call() throws InterruptedException {
                System.out.println("callback work");
                Thread.sleep(1900);
                return true;
            }
        });
    }

    public void get() {
        Futures.addCallback(future, new FutureCallback() {
            public void onSuccess(Object result) {
                System.out.println("get data after success" + new Date().getTime() / 1000);
                System.out.println("complete");
            }

            public void onFailure(Throwable thrown) {
                System.out.println("get data after fail" + new Date().getTime() / 1000);
                System.out.println("fail");
            }
        },service);
    }

}
