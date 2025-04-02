package basicExercise.algorithm.stringandarray;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Assert;
import org.springframework.util.comparator.InstanceComparator;

import java.util.*;

//题目：
//两数之和：给定一个数组和目标值，找到两个数使得它们的和等于目标值。
//最长无重复子串：找到字符串中最长的无重复字符子串。"adfawefafad"
//螺旋矩阵：按螺旋顺序打印二维数组。
//合并两个有序数组：将两个有序数组合并为一个有序数组。
//三数之和：在数组中找到所有和为零的三元组。
//思路：
//哈希表（如两数之和）。
//双指针（如三数之和）。
//滑动窗口（如最长无重复子串）。
public class Sum {
    public List<List<Integer>> findSumTarget(List<Integer> source) {
        List<List<Integer>> result = Lists.newArrayList();
        if (source == null || source.size() < 3) {
            return result;
        }
        source.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(source);

        for (int i = 1; i < source.size() - 1; i++) {
            int before = 0;
            int after = source.size() - 1;
            while (before < i && i < after) {
                int sum = source.get(i) + source.get(before) + source.get(after);
                if (sum == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(source.get(before));
                    list.add(source.get(i));
                    list.add(source.get(after));
                    result.add(list);

                    while (before < i && source.get(before).equals(source.get(before + 1))) {
                        System.out.println(i + "equal b" + before);
                        before++;
                    }
                    while (i < after && source.get(after).equals(source.get(after - 1))) {
                        System.out.println(i + "equal a" + after);
                        after--;
                    }
                    after--;
                    before++;

                } else if (sum > 0) {
                    after--;
                } else if (sum < 0) {
                    before++;
                }
            }
        }
        return result;
    }

    public int[] merge(int[] array1, int m, int[] array2, int n) {
        int i = m - 1;
        int j = n - 1;
        int current = m + n - 1;
        while (current > i && j >= 0) {
            if (i < 0 || array1[i] < array2[j]) {
                array1[current] = array2[j--];
            } else {
                array1[current] = array1[i--];
            }
            current--;
        }
        return array1;

    }

    public List<Integer> printLuoXuan(int[][] arrayData) {
        int left = 0;
        int right = arrayData[0].length - 1;
        int top = 0;
        int down = arrayData.length - 1;
        int i = 0, j = 0;
        List<Integer> result = Lists.newArrayList();
        while (left <= right && top <= down) {
            for (j = left; j <= right; j++) {
                result.add(arrayData[top][j]);
            }
            top++;
            if (top > down) {
                break;
            }
            for (i = top; i <= down; i++) {
                result.add(arrayData[i][right]);
            }
            right--;
            if (right < left) {
                break;
            }
            for (j = right; j >= left; j--) {
                result.add(arrayData[down][j]);
            }
            down--;
            for (i = down; i >= top; i--) {
                result.add(arrayData[i][left]);
            }
            left++;
        }
        return result;


    }

    public String findLongest(String search) {
        if (search == null || search.length() <= 1) {
            return search;
        }
        int min = 0;
        int max = 0;
        int left = 0;
        int right = 0;
        Map<Character, Integer> counter = new HashMap<>();
        while (right < search.length()) {
            Character current = search.charAt(right);
            counter.put(current, counter.getOrDefault(current, 0) + 1);
            right++;
            while (counter.get(current) > 1) {
                counter.put(search.charAt(left), counter.get(search.charAt(left)) - 1);
                left++;
            }
            if (right - left > max - min) {
                max = right;
                min = left;
            }
        }
        return search.substring(min, max);
    }


    public boolean findTarget(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        Map<Integer, Integer> flags = new HashMap<>();
        Integer smallest = nums[0];
        Integer largest = nums[0];
        flags.put(nums[0], 1);
        for (int i = 1; i < nums.length; i++) {
            if (flags.get(nums[i]) == null) {
                flags.put(nums[i], 1);
            } else {
                flags.put(nums[i], flags.get(nums[i]) + 1);
            }
            if (nums[i] < smallest) {
                smallest = nums[i];
            }
            if (nums[i] > largest) {
                largest = nums[i];
            }
        }
        if (smallest > target) {
            return false;
        }
        for (Map.Entry<Integer, Integer> entry : flags.entrySet()) {
            if (target - entry.getKey() != entry.getKey() && flags.get(target - entry.getKey()) != null) {
                System.out.println(entry.getKey() + " " + (target - entry.getKey()));
                return true;
            } else if ((target - entry.getKey()) == entry.getKey() && flags.get(target - entry.getKey()) > 1) {
                System.out.println(entry.getKey() + " " + (target - entry.getKey()));
                return true;
            }
        }
        return false;
    }

    // 测试
    public static void main(String[] args) {
        int[][] nums = {{2, 7, 11, 15}, {2, 2}, {1, 3}};
        int[] target = {9, 4, 2};
        for (int i = 0; i < 3; i++) {
            System.out.println(new Sum().findTarget(nums[i], target[i]));
        }

        String search = "asdfqefaqqq";
        Assert.assertEquals("asdfqe", new Sum().findLongest(search));

        int[][] arrayData = {{2, 7, 11, 15}, {2, 3, 4, 2}, {1, 4, 3, 3}};
        System.out.println(new Sum().printLuoXuan(arrayData));

        System.out.println(Arrays.toString(new Sum().merge(new int[]{2, 7, 11, 15, 0, 0, 0, 0}, 4, new int[]{2, 2, 3, 4}, 4)));

        System.out.println(new Sum().findSumTarget(Arrays.asList(-1, -3, 2, 7, 11, 15, 0, 0, 0, 0)));

    }
}
