package basicExercise.thread;

/**
 * Created by jinyan.cao on 2016/11/13.
 */
public class Main {
    public static void main(String[] args) {
        try {
//            InterruptedThread thread = new InterruptedThread();
//            thread.start();
//            Thread.sleep(1000L);
//            thread.interrupt();


//            Thread1 thread1 = new Thread1();
//            thread1.setDaemon(true);
//            thread1.start();
//            Thread.sleep(5000);
//            System.out.println("stop");
            ThreadTestService service = new ThreadTestService();
            Thread2 thread2 = new Thread2(service);
            thread2.start();
            Thread3 thread3 = new Thread3(service);
            thread3.start();

        } catch (Exception e) {
            System.out.println("exception");
        }
        System.out.println("end");
    }


}

class Thread1 extends Thread {
    private int i = 0;

    public void run() {
        try {
            while (true) {
                i++;
                System.out.println("i=" + i);
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class Thread2 extends Thread {
    private ThreadTestService service;

    public Thread2(ThreadTestService service) {
        this.service = service;
    }

    public void run() {
        service.methodA();
    }
}

class Thread3 extends Thread {
    private ThreadTestService service;

    public Thread3(ThreadTestService service) {
        this.service = service;
    }

    public void run() {
        service.methodB();
    }
}