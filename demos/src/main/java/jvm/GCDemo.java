package jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GCDemo {
    static List<String> list = new ArrayList<>();
    static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    // 静态内部类，模拟内存泄漏
    static class LeakyClass {
        static List<Object> leakyList = new ArrayList<>();
    }
    public static void main(String[] args) {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                list.add(new String(new byte[1024 * 1000]));
                System.out.println(list.get(list.size() - 1));
            }
        }, 0, 1, TimeUnit.MILLISECONDS);

        // 模拟内存泄漏
        for (int i = 0; i < 1000; i++) {
            LeakyClass.leakyList.add(new byte[1024 * 1024]); // 每次添加 1MB 数据
        }
    }
}

//运行： java -Xms50m -Xmx50m jvm.GCDemo -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=~/Desktop/dump