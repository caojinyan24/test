package basicExercise.thread.schedule1;

/**
 * Created by jinyan.cao on 2017/4/3.
 */
public abstract class ScheduleFutureTask implements Comparable {
    protected long nextExecuteTime;//下次执行时间
    protected long taskType;//任务类型,Delay:0;repeat:1
    Runnable runnable;

    public ScheduleFutureTask(Runnable runnable, long nextExecuteTime, long taskType) {
        this.nextExecuteTime = nextExecuteTime;
        this.taskType = taskType;
        this.runnable = runnable;
    }

    @Override
    public int compareTo(Object o) {
        ScheduleFutureTask scheduleFutureTask = (ScheduleFutureTask) o;
        if (this.getNextExecuteTime() < scheduleFutureTask.getNextExecuteTime()) {
            return -1;
        } else if (this.getNextExecuteTime() == scheduleFutureTask.getNextExecuteTime()) {
            return 0;
        } else {
            return 1;
        }
    }
    /**
     * 在新线程中执行，并更新下次执行时间和主线程会有时间差，会存在取到未更新执行时间的task，导致任务重复执行
     * 解决办法：把之前的call分为两步执行，加入beforeExecute方法
     */
    abstract void beforeExecute();

    abstract void call();

    abstract long getNextExecuteTime();
}
