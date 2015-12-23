package org.basic.common.bean;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdkLoggerTest {

//    -Djava.util.logging.config.file=logging.properties
    public static int myRenderDelay = 0;
    public static boolean myTestModeEnabled = false;
    public static final String LOGGER_NAME = "RRLOG";
    private static Logger myLogger = Logger.getLogger(LOGGER_NAME);
    private static FileHandler myLogFile = null;

    public static void main(String[] args) {
        startLogging();
//        myLogger.setUseParentHandlers(false);
        myLogger.info("test");

    }

    private static void startLogging() {

        try {
            Properties logProperties = new Properties();
            try {
                InputStream is = JdkLoggerTest.class.getResourceAsStream("/rrlog.properties");
                logProperties.load(is);
                is.close();
            } catch (Exception ex) {
            }
            Level logLevel = Level.parse(logProperties.getProperty("LEVEL", "ALL"));
            String logFilename = "NotFound";
            if (!logFilename.equals("NotFound")) {
                String fileSeparator = System.getProperty("file.separator", "/");
                logFilename += fileSeparator;
                logFilename += "log";
                logFilename += fileSeparator;
                logFilename += "RptRender%u.log";
                myLogFile = new FileHandler(logFilename, 5000000, 5, true);
                myLogFile.setFormatter(new SimpleFormatterX());
                myLogger.addHandler(myLogFile);
            } else {
                logFilename = "RptRender.log";
                myLogFile = new FileHandler(logFilename, 5000000, 5, true);
                myLogFile.setFormatter(new SimpleFormatterX());
                myLogger.addHandler(myLogFile);
            }
            String testModeEnableString = logProperties.getProperty("TEST_MODE", "false");
            myTestModeEnabled = Boolean.parseBoolean(testModeEnableString);
            if (myTestModeEnabled) {
                /**************************************
                 * Test code begin
                 */
                StringBuffer delayStringProp = new StringBuffer("DELAY_");
                delayStringProp.append(Integer.toString(-1));
                String delayString = logProperties.getProperty(delayStringProp.toString(), "50");
                myRenderDelay = Integer.parseInt(delayString);
                /*****************************************
                 * Test code end
                 */
            }
        } catch (Exception ex) {
            myLogger.setLevel(Level.parse("OFF"));
        }
    }

}
