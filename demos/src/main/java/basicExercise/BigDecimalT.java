package basicExercise;

import java.math.BigDecimal;

/**
 * Created by jinyancao on 2016/06/30/10/29
 */
public class BigDecimalT {
    /**
     * bigdecimal转化为2位精度的字符串
     * @param amount amount
     * @return string
     */
    public static String toStringWith2Scale(BigDecimal amount) {
        if (null == amount) {
            return "0.00";
        } else {
            return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        }
    }

    public static void main(String[] args) {
        java.math.BigDecimal abc = new java.math.BigDecimal("11110.9548881");
        System.out.println(String.valueOf(abc));
        System.out.println(toStringWith2Scale(abc));
    }
}
