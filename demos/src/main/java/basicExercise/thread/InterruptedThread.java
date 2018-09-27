package basicExercise.thread;

/**
 * Created by jinyan.cao on 2016/11/13.
 */
public class InterruptedThread extends Thread {
    public void run() {
        super.run();
        for (int i = 0; i < 500000; i++) {
            if (this.isInterrupted()) {
                System.out.println("stopped");
                break;
            }
            System.out.println("i=" + i);
        }
        System.out.println("outer for");
    }
}
