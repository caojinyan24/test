package basicExercise;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinyan.cao on 2016/10/3.
 */
public class Programming {
    /**
     * 给定一个数组和最大长度，找出所有的组合
     * 要求，数组只包含"a","b",其中两个a不能相邻。
     * 如给定2，输出：ab，ba，bb
     *
     * @param list 递归前数组
     * @return 所有符合条件的数组
     */
    private Integer max = 3;

    public List<ArrayList<String>> getAllArray(List<ArrayList<String>> list) {
        List<ArrayList<String>> result = Lists.newArrayList();
        if (list.size() == 0) {
            result.add(Lists.newArrayList("a"));
            result.add(Lists.newArrayList("b"));
        } else if (list.get(0).size() == max) {
            return list;
        } else {
            for (int i = 0; i < list.size() && list.get(0).size() <= max; i++) {

                if (list.get(i).get(list.get(i).size() - 1).equals("a")) {
                    list.get(i).add("b");
                    result.add(list.get(i));
                } else {
                    List<String> a = Lists.newArrayList(list.get(i));
                    List<String> b = Lists.newArrayList(list.get(i));
                    a.add("a");
                    result.add((ArrayList<String>) a);
                    b.add("b");
                    result.add((ArrayList<String>) b);
                }
            }
        }
        return getAllArray(result);
    }
    public static void main(String[] args) {
        Programming p = new Programming();
        System.out.println(p.getAllArray(Lists.<ArrayList<String>>newArrayList()));
    }
}
