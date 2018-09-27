package basicExercise.thread;

/**
 * Created by jinyan.cao on 2016/10/29.
 */
public class SafeThread extends Thread {
    private  int count=10;
    public void run(){
        count--;
        System.out.println(this.currentThread().getName()+":"+count);
    }

    public static void test1(){
        //多个线程同时处理safeThread这个对象，对象中的变量count同时被多个线程操作。
        SafeThread safeThread=new SafeThread();
        Thread a=new Thread(safeThread,"a");
        Thread b=new Thread(safeThread,"b");
        Thread c=new Thread(safeThread,"c");
        Thread d=new Thread(safeThread,"d");
        Thread e=new Thread(safeThread,"e");
        Thread f=new Thread(safeThread,"f");
        a.run();
        b.run();
        c.run();
        d.run();
        e.run();
        f.run();
    }
    public static void test2(){
        //每个对象单独处理，除非count是静态变量
        SafeThread thread1=new SafeThread();
        SafeThread thread2=new SafeThread();
        SafeThread thread3=new SafeThread();
        SafeThread thread4=new SafeThread();
        SafeThread thread5=new SafeThread();
        SafeThread thread6=new SafeThread();
        SafeThread thread7=new SafeThread();
        SafeThread thread8=new SafeThread();
        thread1.run();
        thread2.run();
        thread3.run();
        thread4.run();
        thread5.run();
        thread6.run();
        thread7.run();
        thread8.run();
    }
    public static void main(String[]args){
        test1();
        test2();

    }

}
