package org.basic.io.log;

/**
 * <pre>
 * SLF4J，即简单日志门面（Simple Logging Facade for Java），不是具体的日志解决方案，它只服务于各种各样的日志系统。
 * 按照官方的说法，SLF4J是一个用于日志系统的简单Facade，允许最终用户在部署其应用时使用其所希望的日志系统。
 * 
 * org/slf4j/impl/StaticLoggerBinder.class (Default implementation) ------Override the class below
 *      Log4jLoggerAdapter.class
 *      Log4jLoggerFactory.class
 *      Log4jMDCAdapter.class
 *      StaticLoggerBinder.class
 *      StaticMarkerBinder.class
 *      StaticMDCBinder.class
 * 
 * Commonly-used implementation:
 *         slf4j-api-1.7.5.jar
 *         slf4j-ext-1.7.5.jar
 *         slf4j-jcl-1.7.5.jar
 *         slf4j-log4j12-1.7.5.jar
 *         slf4j-simple-1.7.5.jar
 * 
 * </pre>
 */
public class SLF4JTest {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SLF4JTest.class);
    Integer t;
    Integer oldT;

    public void setTemperature(Integer temperature) {
        oldT = t;
        t = temperature;
        Object[] objs = { new java.util.Date(), oldT, t };
        logger.info("Today is {}, Temperature set to {}. Old temperature was {}.", objs);

        if (temperature.intValue() > 50) {
            logger.warn("Temperature({}) has risen above 50 degrees.", t);
        }
    }

    public static void main(String[] args) {
        SLF4JTest slf4Test = new SLF4JTest();
        slf4Test.setTemperature(10);
        slf4Test.setTemperature(60);
    }
}
