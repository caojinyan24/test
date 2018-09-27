package basicExercise.Clonable;

/**
 * Created by jinyancao on 2/7/17.
 */
class Student implements Cloneable {
    String name;// 常量对象。
    int age;
    Professor p;// 学生1和学生2的引用值都是一样的。

    Student(String name, int age, Professor p) {
        this.name = name;
        this.age = age;
        this.p = p;
    }

    public Object clone() {
        Student o = null;
        try {
            o = (Student) super.clone();
//            o.p = (Professor) p.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Student{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", p=").append(p);
        sb.append('}');
        return sb.toString();
    }
}
