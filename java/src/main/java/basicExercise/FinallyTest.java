package basicExercise;

/**
 * 1. 在catch中的return语句执行之前，先把返回结果暂存，然后执行finally中的代码
 * 2. 在catch中若throw异常，在throw之前先执行finally中的代码
 * Created by jinyancao on 2/6/17.
 */
public class FinallyTest {
    public int test1() {
        int ret = 0;
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("catch" + ret);
            ret = 1;
            System.out.println("return" + ret);
            return ret;
        } finally {
            System.out.println("finally" + ret);
            ret = 2;
            System.out.println("finallyAfter" + ret);

        }
    }

    boolean testEx() throws Exception {
        boolean ret = true;
        try {
            ret = testEx1();
        } catch (Exception e) {
            System.out.println("testEx, catch exception");
            ret = false;
            throw e;
        } finally {
            System.out.println("testEx, finally; return value=" + ret);
            return ret;
        }
    }//throw

    boolean testEx1() throws Exception {
        boolean ret = true;
        try {
            ret = testEx2();
            if (!ret) {
                return false;
            }
            System.out.println("testEx1, at the end of try");
            return ret;
        } catch (Exception e) {
            System.out.println("testEx1, catch exception");
            throw e;
        } finally {
            System.out.println("testEx1, finally; return value=" + ret);
            return ret;
        }//抛异常
    }

    boolean testEx2() throws Exception {
        boolean ret = true;
        try {
            int b = 12;
            int c;
            for (int i = 2; i >= -2; i--) {
                c = b / i;
                System.out.println("i=" + i);
            }
            return true;
        } catch (Exception e) {
            System.out.println("testEx2, catch exception");
            ret = false;
            throw e;
//            System.out.println("testEx2, catch exception after");
//            return ret;
        } finally {
            ret = true;
            System.out.println("testEx2, finally; return value=" + ret);
//            return ret;
        }//抛异常
    }

    public static void main(String[] args) {
        FinallyTest testObject = new FinallyTest();
//        System.out.println(testObject.testEx());
//        System.out.println(testObject.testEx1());
        try {
            testObject.testEx2();
        } catch (Exception e) {
//            e.printStackTrace();
        }
//        System.out.println();


    }
}
