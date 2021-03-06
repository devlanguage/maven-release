package org.basic.io.log;

import java.util.Hashtable;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JdkLogHelper {
    private static LogManager manager = null;
    private final static Hashtable<String, Handler> LOGGER_HANDLERS = new Hashtable<String, Handler>();
    private final static String ROOT_LEVEL = ".level";
    private final static String DELIMETER = "delimeter";
    private final static String DEFAULT_DELIMETER = ",";
    private final static String HANDLER_LIST = "handlers";

    static {
//        LogManager.getLogManager().readConfiguration(
//                JdkLogHelper.class.getClassLoader().getResourceAsStream("logging.properties"));
                
        String localPath = JdkLogHelper.class.getClassLoader().getResource(
                "org/basic/log/logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", localPath.substring(0, localPath
                .length()));
        manager = LogManager.getLogManager();
        String delimeter = manager.getProperty(DELIMETER) == null ? DEFAULT_DELIMETER : manager
                .getProperty(DELIMETER);
        for (String handlerName : manager.getProperty(HANDLER_LIST).split(delimeter)) {
            try {
                Handler handler = (Handler) Class.forName(handlerName).newInstance();
                // 如果需要可以在这里加些对Handler的定制
                // 比如
                // if (handler instanceof FileHandler) {
                // DO Configuration
                // } else if (handler instanceof EmailHandler) {
                // DO Configuration.
                // }
                LOGGER_HANDLERS.put(handlerName, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public final static void addHandlers(Logger logger) {
        for (Handler handler : LOGGER_HANDLERS.values()) {
            logger.addHandler(handler);
        }
    }
    public final static String getProperty(String key, String defaultValue) {
        if (manager.getProperty(key) == null) {
            return defaultValue;
        } else {
            return manager.getProperty(key).trim();
        }
    }
    public final static String getProperty(String key) {
        return getProperty(key, null);
    }
    public final static Logger getLogger(Class<?> clazz) {
        Logger logger = null;
        if ((logger = manager.getLogger(clazz.getName())) == null) {
            logger = Logger.getLogger(clazz.getName());
            logger.setLevel(Level.parse(getProperty(ROOT_LEVEL)));
            logger.setUseParentHandlers(false);
            addHandlers(logger);
        }
        return logger;
    }
}
