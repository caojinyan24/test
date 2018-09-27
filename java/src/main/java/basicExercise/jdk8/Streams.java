package basicExercise.jdk8;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by jinyan on 12/22/17 5:51 PM.
 */
public class Streams {
    private static final Logger logger = LoggerFactory.getLogger(Streams.class);

    public static void main(String[] args) {
        final Collection< Task > tasks = Lists.newArrayList(
                new Task(Status.OPEN, 5),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8)
        );
        // Calculate total points of all active tasks using sum()
        final long totalPointsOfOpenTasks = tasks
                .stream()
                .filter( task -> task.getStatus() == Status.OPEN )
                .mapToInt( Task::getPoints )
                .sum();

        System.out.println( "Total points: " + totalPointsOfOpenTasks );
    }
    private enum Status {
        OPEN, CLOSED
    };

    private static final class Task {
        private final Status status;
        private final Integer points;

        Task( final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }

        public Integer getPoints() {
            return points;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }

}
