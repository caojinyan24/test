package basicExercise.Clonable;

/**
 * Created by jinyancao on 2/7/17.
 */
class Professor implements Cloneable {
    String name;
    int age;

    Professor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Professor o=null;
        try {
            o = (Professor) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Professor{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
