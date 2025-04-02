package basicExercise.algorithm.strings;

import java.util.HashMap;
import java.util.Map;

//代码:给定一个字符串, 输出按字母序排序后,字符串中每个字母排序后的索引.
//        示例：给定字符串acbe， 按照字母序排序后是abce, 即排序的字符串中，a索引为1, b索引为2, c索引为3, e索引为4
//        按照acbe的顺序输出排序后每个字母的索引序号，即: 1, 3, 2, 4
public class StrIndex {
    String SortedIndex(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        //数组初始化
        char[] bucket = new char[26];
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 'a';
            bucket[index] = 1;
        }
        //桶排序
        Map<Character, Integer> indexMap = new HashMap<Character, Integer>();
        int j = 0;
        for (int i = 0; i < 26; i++) {
            if (bucket[i] == 1) {
                indexMap.put((char) ('a' + i), ++j);
            }
        }
        //输出结果
        StringBuilder result= new StringBuilder();
          for (int i = 0; i < str.length(); i++) {
              result.append(indexMap.get(str.charAt(i)));
          }
          return result.toString();
    }
}
