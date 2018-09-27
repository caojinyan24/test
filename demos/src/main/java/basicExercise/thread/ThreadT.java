package basicExercise.thread;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates ten threads to search for the maximum value of a large matrix.
 * Each thread searches one portion of the matrix.
 * From：http://www.ibm.com/developerworks/cn/education/java/j-threads/j-threads.html
 * Created by jinyancao on 10/6/16.
 */
public class ThreadT extends Thread {


    int max = Integer.MIN_VALUE;
    List<Integer> ourArray;

    public ThreadT(List<Integer> ourArray) {
        this.ourArray = ourArray;
    }

    // Find the maximum value in our particular piece of the array
    public void run() {
        for (int i = 0; i < ourArray.size(); i++)
            max = Math.max(max, ourArray.get(i));
    }

    public int getMax() {
        return max;
    }

    private static List<List<Integer>> getBigHairyMatrix() {
        List<Integer> a = Lists.newArrayList(1, 4, 9, 7);
        List<Integer>b=Lists.newArrayList(2,5);
        return Lists.newArrayList(a, b, a, a);
    }

    public static void main(String[] args) {
        List<List<Integer>> bigMatrix = getBigHairyMatrix();
        List<ThreadT> threads = new ArrayList<ThreadT>(bigMatrix.size());

        int max = Integer.MIN_VALUE;

        // Give each thread a slice of the matrix to work with
        for (int i = 0; i < bigMatrix.size(); i++) {
            threads.add(new ThreadT(bigMatrix.get(i)));
            threads.get(i).start();
        }

        // Wait for each thread to finish
        try {
            for (int i = 0; i < bigMatrix.size(); i++) {
                threads.get(i).join();//主线程等待threads[i]调用结束再进行下一步操作。
                max = Math.max(max, threads.get(i).getMax());
            }
        } catch (InterruptedException e) {
            // fall through
        }

        System.out.println("Maximum value was " + max);
    }
}