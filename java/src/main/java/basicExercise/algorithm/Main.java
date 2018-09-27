package basicExercise.algorithm;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by jinyan.cao on 2017/2/12.
 */
public class Main {
    public static void main(String[] args) {
//        new Main().generateAndSort();
        new Main().chuquan();

    }

    /**
     * 随机产生20个字符并且字符串不能重复 且进行排序
     * 这里不区分大小写
     */
    public void generateAndSort() {
        Random random = new Random();
        Set<Integer> set = Sets.newTreeSet();
        while (set.size() != 20) {
            set.add(Math.abs(random.nextInt() % 26));
        }
        for (Integer integer : set) {
            System.out.print(String.valueOf((char) (integer + 97)));
        }
    }

    /**
     * 50个人围成一圈数到三和三的倍数时出圈，问剩下的人是谁？在原来的位置是多少？
     */
    public void chuquan() {
        List<Integer> list = Lists.newArrayList();
        for (Integer i = 0; i < 50; i++) {
            list.add(i);
        }
        System.out.println(list);
        int i = 1, j = 1;
        while (true) {
            if (j % 3 == 0) {
                list.set(i, null);
                System.out.println(i+","+j);
            }
            if (Sets.newHashSet(list).size() == 2) {
                break;
            }
            i++;
            j++;
            if (i == 50) {
                i = 0;
            }
        }
        System.out.println(list);
    }
    /**
     * 在控制台输入数字（0、1、2、3、4、5、6、7、8、9、10……）
     在控制台输出中文大写（零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾……）
     101 --》壹佰零壹
     100010 --》拾万零壹拾
     */


    /**
     * http://www.cnblogs.com/nokiaguy/p/3405149.html
     */

}

