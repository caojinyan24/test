package basicExercise.java.mbean;

/**
 * Created by jinyan on 3/28/18 5:21 PM.
 */
public class User implements UserMBean {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public int add(int x, int y) {
        return x + y;
    }
    public void test(){
        System.out.println("test!!!");
    }
}



