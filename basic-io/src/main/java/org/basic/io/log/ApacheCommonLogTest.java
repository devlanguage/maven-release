package org.basic.io.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.LogFactory;

public class ApacheCommonLogTest {

    final static org.apache.commons.logging.Log randomLogger = LogFactory.getLog("any low");
    
    final static org.apache.commons.logging.Log logger = LogFactory.getLog("ApacheCommonLogTest");
    final static org.apache.commons.logging.Log catogery01 = LogFactory.getLog("selfdefined.category01");
    final static org.apache.commons.logging.Log logger01 = LogFactory.getLog("selfdefined.logger01");
    final static org.apache.commons.logging.Log logger001 = LogFactory.getLog("selfdefined.logger01.logger001");

    public static void main(String[] args) {

        logger.info("source/basic/src");
        try {
            Connection conn = DriverManager.getConnection("sadfa'");
        } catch (SQLException e) {
            logger.error("mainad", e);
        }
        catogery01.info("catogery01, test");
        logger01.info("logger01, test");
        logger001.info("logger001, test");
        randomLogger.info("log4j, apache logging test");
        
    }
}
