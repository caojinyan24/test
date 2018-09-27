package basicExercise.future;

/**
 * fucture测试类
 * 实际使用可以在把所有任务放入future中，另一个线程循环遍历，从中获取取得的结果并进行后续处理
 * Created by jinyancao on 2/10/17.
 */
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                new FutureProcessor().send();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                new FutureProcessor().get();
            }
        });
        thread1.start();
        thread2.start();

    }


}
