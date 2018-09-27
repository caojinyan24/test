package basicExercise.java.mbean;

/**
 * Created by jinyan on 3/28/18 5:21 PM.
 */
public interface UserMBean {

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    int add(int x, int y);

    public void test();

}
