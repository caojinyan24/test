package basicExercise.thread.schedule1;

import java.util.Date;

/**
 * 按照一定频率执行的任务，无延时时间设置
 * Created by jinyan.cao on 2017/4/3.
 */
public class RepeatedScheduleFutureTask extends ScheduleFutureTask {
    private long period;//执行间隔，以毫秒为单位

    public RepeatedScheduleFutureTask(Runnable runnable, long period) {
        super(runnable, new Date().getTime(), 1);
        this.period = period;
    }

    @Override
    public long getNextExecuteTime() {
        return nextExecuteTime;
    }

    @Override
    void beforeExecute() {
        this.nextExecuteTime += period;
    }

    @Override
    public void call() {
        runnable.run();

    }

}
