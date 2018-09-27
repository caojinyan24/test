package basicExercise.thread;

/**
 * Created by jinyan.cao on 2016/10/29.
 */

public class LoginServlet {
    private static String usernameRef;
    private static String passwordRef;

    synchronized public static void doPost(String username, String password) {
        try {
            usernameRef = username;
            if (username.equals("a")) {
                Thread.sleep(3000l);
            }
            passwordRef = password;
            System.out.println("username=" + usernameRef + ".password=" + passwordRef);
        } catch (Exception e) {

        }
    }

    class A extends Thread {
        public void run() {
            LoginServlet.doPost("a", "aa");
        }
    }

    class B extends Thread {
        public void run() {
            LoginServlet.doPost("b", "bb");
        }
    }


    public static void main(String[] args) {
        LoginServlet loginServlet = new LoginServlet();
        A a = loginServlet.new A();
        a.start();
        B b = loginServlet.new B();
        b.start();
    }
}
