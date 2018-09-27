package basicExercise.thread;

/**
 * Created by jinyan.cao on 2016/12/3.
 */
public class ThreadTestService {
    public synchronized void methodA() {
        System.out.println("methodA begin");
        boolean isContinueRun = true;
        while (isContinueRun) {

        }
        System.out.println("methodA end");
    }

    synchronized public void methodB() {
        System.out.println("methodB begin");
        System.out.println("methodB end");

    }

    public void methodC() {
        System.out.println("methodC begin");
        System.out.println("methodC end");

    }
}
