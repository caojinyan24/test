package basicExercise.thread.schedule1;

import java.util.Date;

/**
 * 延迟执行
 * Created by jinyan.cao on 2017/4/3.
 */
public class DelayScheduleFutureTask extends ScheduleFutureTask {

    public DelayScheduleFutureTask(Runnable runnable, long delayTime) {
        super(runnable, new Date().getTime() + delayTime, 0);
    }

    @Override
    public long getNextExecuteTime() {
        return nextExecuteTime;
    }

    @Override
    void beforeExecute() {
        nextExecuteTime = -1;
    }

    @Override
    public void call() {
        runnable.run();//todo:怎么想办法获得执行结果
    }




}
