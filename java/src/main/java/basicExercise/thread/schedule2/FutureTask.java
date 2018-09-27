package basicExercise.thread.schedule2;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by jinyancao on 4/11/17.
 */
public class FutureTask  {
    private Runnable runnable;
    private long executeTime;

    public FutureTask(Runnable runnable, long executeTime) {
        this.runnable = runnable;
        this.executeTime = executeTime;
    }

    public Runnable getRunnable() {
        return runnable;
    }


    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }
}
