package basicExercise.Clonable;

/**
 * Object中的clone接口默认是浅克隆，由于String的简单对象均继承子Object对象可实现复制，但对象内的对象复制的是对象的引用，对克隆对象中的对象做变更，会影响原有对象中的对象。
 * 怎么实现深克隆？对象中的对象实现Clonable接口，同时在要克隆的对象中，手工调用对象中的对象的clone接口。
 * 实现深克隆的另一种方式：将对象写入到流中，然后再从流中读出。
 * Created by jinyancao on 2/7/17.
 */
public class ClonableTest {
    public static void main(String[] args) {
        Professor p = new Professor("wangwu", 50);
        Student s1 = new Student("zhangsan", 18, p);
        Student s2 = (Student) s1.clone();
        s2.p.name = "lisi";
        s2.p.age = 30;
        s2.age = 20;
        s2.name="s2.name";
        System.out.println("name=" + s1.p.name + "," + "age=" + s1.p.age);
        System.out.println("s1=" + s1 + "," + "s2=" + s2);
    }


}
