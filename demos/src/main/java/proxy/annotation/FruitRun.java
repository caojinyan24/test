package proxy.annotation;

/**
 * Created by jinyan.cao on 2016/3/27.
 * 1. 注解处理器是干什么的：处理注解，根据注解进行相关操作
 * 2. 什么时候用注解比较好：
 */
public class FruitRun {
    /**
     * @param args
     */
    public static void main(String[] args) {

        Apple apple=new Apple();
        apple.setAppleColor("red");
        apple.setAppleName("apple");
        apple.setAppleProvider("appleProvider");
        FruitInfoUtil.getFruitInfo(apple);

    }
}