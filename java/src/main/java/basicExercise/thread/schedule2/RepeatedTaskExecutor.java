package basicExercise.thread.schedule2;

import java.util.concurrent.BlockingQueue;

/**
 * Created by jinyancao on 4/11/17.
 */
public class RepeatedTaskExecutor extends TaskExecutor {

    public RepeatedTaskExecutor(BlockingQueue<FutureTask> queue) {
        super(queue);
    }
}
