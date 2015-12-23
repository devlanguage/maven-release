package org.basic.jvm.hello;

import java.util.Enumeration;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log4jTester {

    final static Logger logger = Logger.getLogger("categorytest.test1");

    public static void main(String[] args) {

        Category category = Logger.getRootLogger();
//        System.out.println(category.getHierarchy());
        // log4j.category.categorytest.debug1=DEBUG
        // log4j.category.categorytest.info1=INFO
        // log4j.category.categorytest.warn1=WARN
        for (Enumeration<Category> enum1 = Logger.getCurrentCategories(); enum1.hasMoreElements();) {
            System.out.println(enum1.nextElement().getName());
        }
        logger.log("debug", Priority.DEBUG, "Log4jTest.main", null);
    }
}
