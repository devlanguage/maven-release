package org.basic.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {
    static{
       PropertyConfigurator.configure(Log4jTest.class.getResource("log4j.properties"));
       
        
    }

    final static Logger logger = Logger.getLogger("Log4jTest");
    final static Logger catogery01 = Logger.getLogger("selfdefined.category01");
    final static Logger logger01 = Logger.getLogger("selfdefined.logger01");
    final static Logger logger001 = Logger.getLogger("selfdefined.logger01.logger001");

    public static void main(String[] args) {

        logger.info("source/basic/src");
        try {
            Connection conn = DriverManager.getConnection("sadfa'");
        } catch (SQLException e) {
            logger.log(Level.ERROR, "mainad", e);
        }
        logger.fatal("Test _message");

        printMessage(catogery01, "catogery01");
        printMessage(logger01, "logger01");
        printMessage(logger001, "logger001");

    }

    public static final void printMessage(Logger logger, String loggerName) {

        logger.log(Level.DEBUG, loggerName + ", DEBUG");
        logger.log(Level.INFO, loggerName + ", INFO");
        logger.log(Level.WARN, loggerName + ", WARN");
        logger.log(Level.ERROR, loggerName + ", ERROR");
        logger.log(Level.FATAL, loggerName + ", FATAL");
    }
}
