package basicExercise;

/**
 * Created by jinyancao on 2016/06/26/13/56
 */
public class StaticDispatch {
    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    /**
     * 重载类型是在编译期确定的，重载时根据变量的静态类型来确定调用哪个方法
     */
    public void sayHello(Human guy) {
        System.out.println("hello,guy");
    }

    public void sayHello(Woman woman) {
        System.out.println("hello,woman");
    }

    public void sayHello(Man man) {
        System.out.println("hello,man");

    }

    public void sayHello(int a){
        System.out.println("int");
    }
    public void sayHello(char a){
        System.out.println("char");

    }

    public void excute() {
        Human man = new Man();
        Human woman = new Woman();
        this.sayHello(man);
        this.sayHello(woman);
        this.sayHello((Man) man);
        this.sayHello((Woman) woman);
        this.sayHello('a');
        this.sayHello(1);
    }

    public static void main(String[] args) {
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.excute();
    }


}
