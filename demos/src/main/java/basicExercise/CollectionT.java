package basicExercise;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * 循环中改变集合元素的时候会校验是否可改变。在循环中会改变集合结构，每次操作remove或next的时候都会校验容器结构是否发生变化。
 * Created by jinyancao on 2016/07/03/18/52
 */
public class CollectionT {

//    public static void main(String[] agrs) {
//        List<String> iterator = new ArrayList<String>();
//        iterator.add("a");
//        iterator.add("b");
//        iterator.add("c");
////        for (String s : iterator) {
////            if (s.equals("a")) {
////                System.out.println(s);
////                iterator.remove(s);
////            }
////        }
//        iterator.remove(iterator.iterator().next());
//        iterator.remove(iterator.iterator().next());
//        iterator.remove(iterator.iterator().next());
//        System.out.println(iterator);
//    }
//    public static void main(String[] agrs) {
//        List<String> iterator = Lists.newArrayList("a", "b", "c");
//        Iterator<String>iter=iterator.iterator();
//        while (iter.hasNext()){
//            iterator.remove(iter.next());
//        }
//        iterator.remove(1);
//        System.out.println(iterator);
//    }

    public static void main(String[] agrs) {
        List<String> iterator = Lists.newArrayList("a", "b", "c");
        Iterator<String> iter = iterator.iterator();
        Integer a = 0;
        while (iter.hasNext()) {
            if (a > 0) {
                iter.remove();
            }
            a++;
        }
        System.out.println(iterator);
    }
}
