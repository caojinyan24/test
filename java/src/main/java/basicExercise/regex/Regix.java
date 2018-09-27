package basicExercise.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinyancao on 1/12/17.
 * 1. 规则匹配
 * 2. 根据匹配的字符串做修改
 * 3.
 */
public class Regix {
    public static void main(String[] args) {
        Regix r = new Regix();
        r.numberMatcher();
    }

    public void numberMatcher() {
        try {
            BufferedReader in;
            Pattern pattern = Pattern.compile("[0-9]{3}");//可以
//            Pattern pattern = Pattern.compile("/d{3}");//不对
//            Pattern pattern = Pattern.compile("^[A-Za-z]+$");//可以
            File file = new File(this.getClass().getResource("/phone").getPath());
            in = new BufferedReader(new FileReader(file));
            String s;
            while ((s = in.readLine()) != null) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    System.out.println(s);
                    s = s.replace(matcher.toMatchResult().group(), "def");
                    System.out.println(s + ":" + matcher.group());
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
