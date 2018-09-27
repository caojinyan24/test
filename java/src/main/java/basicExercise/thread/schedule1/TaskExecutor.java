package basicExercise.thread.schedule1;

import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * 任务执行者
 * Created by jinyan.cao on 2017/4/3.
 */
public class TaskExecutor {
    PriorityBlockingQueue<ScheduleFutureTask> tasks = new PriorityBlockingQueue<>();
    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    };

    public void schedule(ScheduleFutureTask scheduleFutureTask) {
        addWork(scheduleFutureTask);
    }

    /**
     * 每次执行完之后都再添加一次，应该把添加逻辑放入到需要重复执行的相关类中实现,这样
     *
     * @param scheduleFutureTask
     */
    private void addWork(ScheduleFutureTask scheduleFutureTask) {
        tasks.add(scheduleFutureTask);
        start();

    }


    private void start() {
        while (!tasks.isEmpty()) {
            ScheduleFutureTask toExecuteTask = tasks.peek();
            if (toExecuteTask.getNextExecuteTime() < 0) {
                tasks.remove(toExecuteTask);
            } else {
                while (new Date().getTime() < toExecuteTask.getNextExecuteTime()) {
                }
                toExecuteTask.beforeExecute();
                threadFactory.newThread(toExecuteTask::call
                ).start();
            }
        }
    }


}
