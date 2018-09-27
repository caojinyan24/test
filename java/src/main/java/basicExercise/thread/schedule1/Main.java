package basicExercise.thread.schedule1;

import java.util.Date;

/**
 * todo:
 * 1. 所有任务保持在一个list
 * 2.不要循环阻塞等待
 * Created by jinyan.cao on 2017/4/4.
 */
public class Main {
    private static TaskExecutor taskExecutor = new TaskExecutor();
    static long beginTime = System.nanoTime();

    public static void main(String[] args) {
        System.out.println("delay begin:" + getTimeSlipInSecond());
        taskExecutor.schedule(new DelayScheduleFutureTask(
                () -> System.out.println("execute_delay:" + getTimeSlipInSecond()), 1000L));
        System.out.println("schedule1 begin:" + getTimeSlipInSecond());
        taskExecutor.schedule(new RepeatedScheduleFutureTask(
                () -> System.out.println("execute_repeate:" + getTimeSlipInSecond()), 1000L));
        System.out.println("end:" + getTimeSlipInSecond());


    }

    public static long getTimeSlipInSecond() {
        return (System.nanoTime() - beginTime) / (1000 * 1000 * 1000);
    }
}
