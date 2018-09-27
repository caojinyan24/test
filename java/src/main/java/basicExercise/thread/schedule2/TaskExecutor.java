package basicExercise.thread.schedule2;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by jinyancao on 4/11/17.
 */
public class TaskExecutor {
    private BlockingQueue<FutureTask> queue;
    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    };

    public TaskExecutor(BlockingQueue<FutureTask> queue) {
        this.queue = queue;
    }

    public BlockingQueue<FutureTask> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<FutureTask> queue) {
        this.queue = queue;
    }


    /**
     * 每次add的时候执行任务
     *
     * @param work
     */
    public void addWork(FutureTask work) {
        queue.add(work);
        //todo:判断线程状态，看是否可执行任务

        //执行任务
        FutureTask taskInQueue = null;
        while (true) {
            if ((taskInQueue = queue.poll()) != null) {
                threadFactory.newThread(taskInQueue.getRunnable()).start();
            }
        }
    }

    public long getDelayedExecuteTime(long delayTime, TimeUnit timeUnit) {
        return new Date().getTime() + TimeUnit.SECONDS.toSeconds(10);//todo:
    }
}
