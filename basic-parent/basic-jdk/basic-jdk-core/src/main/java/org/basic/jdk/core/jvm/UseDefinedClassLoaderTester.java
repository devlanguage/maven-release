package org.basic.jdk.core.jvm;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 
 */
public class UseDefinedClassLoaderTester {

    // Log logger = LogFactory.getLog(UseDefinedClassLoaderTester.class);

    private final static Logger logger = LoggerManager.getLogger(UseDefinedClassLoaderTester.class);

    public static void main(String[] args) {

        System.out.println(Double.parseDouble("197.50")-192.70);
        System.out.println(197.50-192.70);
        System.out.println(200.50-192.70);
        System.out.println(Double.parseDouble("197.50"));
        
//        System.out.println(ConsoleHandler.class.getName());
//        System.out.println(LogManager.getLogManager().getProperty("java.util.logging.ConsoleHandler.level"));
//        logger.log(Level.FINEST, "test");
//        logger.log(Level.FINER, "test");
//        logger.log(Level.FINE, "test"); 
//        logger.log(Level.CONFIG, "test");
//        logger.log(Level.INFO, "test");
//        logger.log(Level.WARNING, "test");
//        logger.log(Level.SEVERE, "test");
        
//        HelloworldClassLoader1 classLoader1 = new HelloworldClassLoader1(ClassLoader.getSystemClassLoader());
//
//        HelloworldClassLoader2 classLoader2 = new HelloworldClassLoader2(classLoader1);
//
//        Object user;
//        JvmBaseBean organization;
//
//        try {
//            organization = (JvmBaseBean) classLoader1.loadClass("org.insidejvm.hello.Organization").newInstance();
//            System.out.println(organization);
//
//            // classes loaded by the parent ClassLoader are visible to all the
//            // classes loaded
//            // by child ClassLoader
//            user = (JvmBaseBean) classLoader2.loadClass("org.insidejvm.hello.User").newInstance();
//            System.out.println(user);
//
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
