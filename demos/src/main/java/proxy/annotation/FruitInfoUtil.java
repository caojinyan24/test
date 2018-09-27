package proxy.annotation;

/**
 * 注解处理器
 * Created by jinyan.cao on 2016/3/27.
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class FruitInfoUtil {
    public static void getFruitInfo(Object obj) {
        try {
            Class fruitClass = obj.getClass();
            Field[] fields = fruitClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FruitName.class)) {
                    FruitName fruitName=field.getAnnotation(FruitName.class);
                    System.out.println("proxy.annotation--------    "+fruitName.value());
                    Method method=fruitClass.getMethod("getAppleName");
                    System.out.println("---"+method.invoke(obj));
                } else if (field.isAnnotationPresent(FruitColor.class)) {
                    Method method=fruitClass.getMethod("getAppleColor");
                    System.out.println("---"+method.invoke(obj));
                } else if (field.isAnnotationPresent(FruitProvider.class)) {
                    Method method=fruitClass.getMethod("getAppleProvider");
                    System.out.println("---"+method.invoke(obj));
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}