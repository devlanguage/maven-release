package org.basic.io.log;


public class JdkLogTest {

    private final static java.util.logging.Logger logger = JdkLogHelper.getLogger(JdkLogTest.class);

    public static void main(String[] args) {
//        LogManager.getLogManager().readConfiguration(
//                JdkLogTest.class.getClassLoader().getResourceAsStream("logging.properties"));
      
        logger.log(java.util.logging.Level.FINE, "fine");
        logger.log(java.util.logging.Level.SEVERE, "severe");
    }
}
