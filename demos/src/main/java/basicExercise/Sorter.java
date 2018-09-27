package basicExercise;


import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

/**
 * 排序算法
 * Created by jinyan.cao on 2016/9/29.
 */
public class Sorter {
    /**
     * 快排
     *
     * @param list 初始待排list
     * @return 排序后的list
     */
    private List<Integer> quickSort(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }
        Integer key = list.get(0);
        Integer first = 0;
        Integer last = list.size() - 1;
        while (first < last) {
            while (list.get(last) >= key && last > first) {//避免相同值的重复判断
                last--;
            }
            list.set(first, list.get(last));
            while (list.get(first) <= key && first < last) {
                first++;
            }
            list.set(last, list.get(first));
        }
        list.set(first, key);
        quickSort(list.subList(0, first));
        quickSort(list.subList(first + 1, list.size()));//已经做为中间标杆排过序，不需要再次参加排序
        return list;
    }

    /**
     * 快排
     *
     * @param list 初始待排list
     * @return 排序后的list
     */
    public LinkedList<Integer> quickSort(LinkedList<Integer> list) {
        if (list.size() == 0 || list.size() == 1) {
            return list;
        }
        Integer first = 0;
        Integer last = list.size() - 1;
        Integer temp = list.get(0);
        while (first <= last) {
            while (temp < list.get(last)) {
                last--;
            }
            list.set(last, temp);
            while (temp > list.get(first)) {
                first++;
            }
            list.set(first, temp);
        }
        list.set(first, temp);
        quickSort(list.subList(0, first));
        quickSort(list.subList(first + 1, list.size()));
        return list;
    }

    /**
     * 二分法排序
     *
     * @param list 初始待排list
     * @return 排序后的list
     */
    public List<Integer> binarySort(List<Integer> list) {
        if (list.size() <= 1) {
        } else if (list.size() == 2) {
            if (list.get(0) > list.get(1)) {
                Integer temp = list.get(0);
                list.set(0, list.get(1));
                list.set(1, temp);
            }
        } else {
            Integer last = list.get(list.size() - 1);
            if (last < list.get(list.size() / 2)) {
                binarySort(list.subList(0, list.size() / 2));
            } else {
                binarySort(list.subList(list.size() / 2, list.size() - 1));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Sorter sorter = new Sorter();
        List<Integer> list = Lists.newArrayList(10, 7, 6, 6, 6, 8, 9, 6, 210, 7, 6, 6, 6, 8, 9, 6, 2, 3, 6, 2, 9, 10, 100, 44, 3, 6, 8, 10, 7, 6, 6, 6, 8, 9, 6, 210, 7, 6, 6, 6, 8, 9, 6, 2, 3, 6, 2, 9, 10, 100, 44, 3, 6, 8);
        List<Integer> linkedList = Lists.newLinkedList(Lists.<Integer>newArrayList(10, 7, 6, 6, 6, 8, 9, 6, 210, 7, 6, 6, 6, 8, 9, 6, 2, 3, 6, 2, 9, 10, 100, 44, 3, 6, 8, 10, 7, 6, 6, 6, 8, 9, 6, 210, 7, 6, 6, 6, 8, 9, 6, 2, 3, 6, 2, 9, 10, 100, 44, 3, 6, 8));
        Long time1 = System.currentTimeMillis();
        System.out.println(sorter.quickSort(list) + "cost:" + (System.currentTimeMillis() - time1) + "millsec");
        time1 = System.currentTimeMillis();
        System.out.println(sorter.quickSort(linkedList) + "cost:" + (System.currentTimeMillis() - time1) + "millsec");
        time1 = System.currentTimeMillis();
        System.out.println(sorter.binarySort(list) + "cost:" + (System.currentTimeMillis() - time1) + "millsec");

    }

}
