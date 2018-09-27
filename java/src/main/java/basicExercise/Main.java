package basicExercise;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by jinyan.cao on 2017/2/6.
 */
public class Main {
    public static void main(String[] args) {
        Object object = null;
//        System.out.println(object);
//        System.out.println(String.valueOf(null));
//        new Main().method1();
//        new Main().stringTest();
//      List<String>a=  Arrays.asList("a", "b", "c");
//        List<String>b= Lists.newArrayList("a", "b", "c");
////        a.remove(0);
//        b.remove(0);
//        System.out.println(b);

        //removeTest test
//        List<String> a = Lists.newArrayList("a", "c");
//        System.out.println(new Main().removeTest(a));


//        Object[] a1 = Lists.newArrayList("a", "c", "d", "e").toArray();
//        System.arraycopy(a1, 1, a1, 2, 2);
//        System.out.println(Lists.newArrayList(a1));

        String testStr = "abc{JobInfo{jobId=1, appName='hot-deploy', beanName='dataLoaderServiceImpl', methodName='scheduleTest', cronParam='null', address='127.0.0.1', port=8089, param=''}=[JobInfoWrapper{nextExecuteTime=null, currentExecuteTime=null, actualLastExecuteTime=null} JobInfo{jobId=1, appName='hot-deploy', beanName='dataLoaderServiceImpl', methodName='scheduleTest', cronParam='null', address='127.0.0.1', port=8089, param=''}], JobInfo{jobId=1, appName='hot-deploy', beanName='dataLoaderServiceImpl', methodName='scheduleTest', cronParam='0/5 * * * * ?', address='127.0.0.1', port=8089, param='null'}=[JobInfoWrapper{nextExecuteTime=Mon Dec 18 17:39:40 CST 2017, currentExecuteTime=Mon Dec 18 17:39:35 CST 2017, actualLastExecuteTime=Mon Dec 18 17:39:35 CST 2017} JobInfo{jobId=1, appName='hot-deploy', beanName='dataLoaderServiceImpl', methodName='scheduleTest', cronParam='0/5 * * * * ?', address='127.0.0.1', port=8089, param='null'}]}";
//        Map<Object, Object> test = convertToString(testStr);
//        System.out.println(JSONObject.toJSONString(test));


//        Arrays.asList( "a", "b", "d" ).forEach(e ->{ System.out.println( e ) ;});
//        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );


        Optional<String> fullName = Optional.ofNullable(null);//赋值，并设定为允许为null
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));//设定当为null时,展示none
        System.out.println(fullName.orElse("tt"));
        System.out.println(fullName.map(s -> "aaa" + s));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));//如果当前值不为空,执行后边的方法,否则展示stranger
        Optional<String> fullName1 = Optional.ofNullable("test");//赋值，并设定为允许为null
        System.out.println("Full Name is set? " + fullName1.isPresent());
        System.out.println("Full Name: " + fullName1.orElseGet(() -> "[none]"));//设定当为null时,展示none
        System.out.println(fullName1.orElse("tt"));
        System.out.println(fullName1.map(s -> "aaa" + s));//返回的是optional对象
        System.out.println(fullName1.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));//如果当前值不为空,执行后边的方法,否则展示stranger


    }


    public void method1() {
        Integer i = 0;
        Hold temp1 = new Hold();
        temp1.i = 20;
        method2(temp1, i);
        System.out.println(temp1.i + " " + i);

    }

    public void method2(Hold hold, int i) {
        i = 30;
        hold.i = 40;
        // hold = new Hold();
        hold.i = 50;
        System.out.println(hold.i + " " + i);
    }

    public class Hold {
        int i = 10;
    }


    public void stringTest() {
        String a = "abc";
        String b = new String("abc");
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a == b.intern());
        System.out.println(a == b ? "" : "1");
    }

    public Boolean removeTest(Collection<?> c) {
        final Object[] elementData = Lists.newArrayList("a", "b", "c").toArray();
        Integer size = elementData.length;
        Boolean complement = true;
        Integer modCount = 0;

        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement) {
                    elementData[w++] = elementData[r];//包含a和c
                    System.out.println(w);
                    System.out.println(Lists.newArrayList(elementData));
                }
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            if (r != size) {
                System.arraycopy(elementData, r,
                        elementData, w,
                        size - r);
                System.out.println("removeTest:" + Lists.newArrayList(elementData));
                w += size - r;
            }
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++) {
                    elementData[i] = null;
                    System.out.println("removeTest1:" + w + "  " + i);
                    System.out.println("removeTest1:" + Lists.newArrayList(elementData));
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        System.out.println("removeTest1:" + Lists.newArrayList(elementData));
        return modified;
    }

}







