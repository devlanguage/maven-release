package org.basic.aop;
import java.util.Date;
public class AopLogger {
    /**     根据等级记录日志   */
    public static void logging(AopLevel level, String context) {
        if (level.equals(AopLevel.INFO)) {
            System.out.println(new Date() + " " + context);
        }
        if (level.equals(AopLevel.DEBUG)) {
            System.out.println(new Date() + " " + context);
        }
    }
}