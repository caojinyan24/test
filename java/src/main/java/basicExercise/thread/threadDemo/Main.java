package basicExercise.thread.threadDemo;

/**
 * Created by jinyancao on 4/12/17.
 */
public class Main {
    public static void main(String[] args) {
        MultiThreadProcessor multiThreadProcessor = MultiThreadProcessor.getInstance();
        multiThreadProcessor.process(() -> {
            System.out.println("1");
        });
    }
}
