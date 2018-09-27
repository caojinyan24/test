package basicExercise;


/**
 * Created by jinyancao on 2016/06/26/14/33
 */
public class DynamicDispatch {
    /**
     * 重写是动态加载的
     */
    static abstract class Human {
        void sayHello() {
            System.out.println("human");
        }
    }

    static class Man extends Human {
        void sayHello() {
            System.out.println("man");
        }
    }

    static class Woman extends Human {
        void sayHello() {
            System.out.println("woman");
        }
    }

    public static void main(String[] args) {
        Human woman = new Woman();
        Human man = new Man();
        man.sayHello();
        woman.sayHello();
    }


}
