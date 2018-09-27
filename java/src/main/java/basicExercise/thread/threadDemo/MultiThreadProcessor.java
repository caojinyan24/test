package basicExercise.thread.threadDemo;

import java.util.concurrent.*;

/**
 * 多线程
 * Created by jinyan.cao on 2017/2/21.
 */

public class MultiThreadProcessor {
    private static ExecutorService excutorService = Executors.newFixedThreadPool(10);
    private static MultiThreadProcessor multiThreadProcessor = null;

    private MultiThreadProcessor() {
    }

    synchronized public static MultiThreadProcessor getInstance() {//单例模式保证了只创建一个线程池
        if (multiThreadProcessor == null) {
            return new MultiThreadProcessor();
        }
        return multiThreadProcessor;
    }//懒汉模式和饿汉模式

    /**
     * 任务分为两种一种是有返回值的，一种是没有返回值的
     * 无返回值的任务就是一个实现了runnable接口的类.使用run方法
     * 有返回值的任务是一个实现了callable接口的类.使用call方法
     */
    public void process(Runnable runnable) {
        excutorService.execute(runnable);
    }

    /**
     * 通过java.util.concurrent.ExecutorService接口对象来执行任务，该对象有两个方法可以执行任务execute和submit
     * execute这种方式提交没有返回值，也就不能判断是否执行成功。
     * submit这种方式它会返回一个Future对象，通过future的get方法来获取返回值，get方法会阻塞住直到任务完成。
     *
     * @param callable
     */
    public void process1(Callable<Object> callable) throws ExecutionException, InterruptedException {
        Future<Object> result = excutorService.submit(callable);
        Object response = result.get();
        //todo:处理相关响应
    }
}
