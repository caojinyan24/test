package basicExercise.thread.schedule2;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by jinyancao on 4/11/17.
 */
public class DelayedTaskExecutor extends TaskExecutor {
    DelayedBlockingQueue queue = new DelayedBlockingQueue();

    public DelayedTaskExecutor(BlockingDeque queue) {
        super(queue);
    }

    public void schedule(Runnable runnable, long delayTime, TimeUnit timeUnit) {
        FutureTask task = new FutureTask(runnable, getDelayedExecuteTime(delayTime, timeUnit));
        super.addWork(task);

    }

    public void execute() {

    }


    public class DelayScheduleFutureTask extends FutureTask {
        private long delayTime;
        private TimeUnit timeUnit;

        public DelayScheduleFutureTask(long delayTime, TimeUnit timeUnit, Runnable runnable) {
            super(runnable, getDelayedExecuteTime(delayTime, timeUnit));
            this.delayTime = delayTime;
            this.timeUnit = timeUnit;
        }


        public long getDelayTime() {
            return delayTime;
        }

        public void setDelayTime(long delayTime) {
            this.delayTime = delayTime;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }
    }

    public static class DelayedBlockingQueue extends AbstractQueue<Runnable> implements BlockingQueue<Runnable> {

        @Override
        public Iterator<Runnable> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void put(Runnable runnable) throws InterruptedException {

        }

        @Override
        public boolean offer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Runnable take() throws InterruptedException {
            return null;
        }

        @Override
        public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
            return null;//for循环做时间判断
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super Runnable> c) {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super Runnable> c, int maxElements) {
            return 0;
        }

        @Override
        public boolean offer(Runnable runnable) {
            return false;
        }

        @Override
        public Runnable poll() {
            return null;
        }

        @Override
        public Runnable peek() {
            return null;
        }
    }
}
