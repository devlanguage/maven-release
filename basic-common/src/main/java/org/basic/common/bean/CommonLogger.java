package org.basic.common.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.basic.common.util.SystemUtil;

public class CommonLogger {
//		implements org.apache.commons.logging.Log
	// JettyLogg: org.mortbay.log.Logger,
//    public static class CommonJdkLogger extends org.apache.cxf.common.logging.Log4jLogger {
//        public CommonJdkLogger(String name, String resourceBundleName) {
//            super(name, resourceBundleName);
//        }
//    }

	// public static class CommonApacheLogger extends
	// org.apache.commons.logging.impl.Log4JLogger {
	// }

	public static final int OFF = Level.OFF.intValue();
	public static final int FATAL = Level.SEVERE.intValue();
	public static final int SEVERE = Level.SEVERE.intValue();
	public static final int ERROR = Level.SEVERE.intValue();
	public static final int WARN = Level.WARNING.intValue();
	public static final int INFO = Level.FINE.intValue();
	public static final int DEBUG = Level.FINER.intValue();
	public static final int TRACE = Level.FINEST.intValue();

	public static final Map<String, Integer> SUPPORTED_LEVEL_STRING_TO_INT = new HashMap<String, Integer>();
	public static final Map<Integer, String> SUPPORTED_LEVEL_INT_TO_STRING = new HashMap<Integer, String>();

	// Log4J logger instance
	Logger logger;
	private static int nLevel = -1;

	// Performance Logger
	static {

		SUPPORTED_LEVEL_STRING_TO_INT.put("OFF", Integer.valueOf(OFF));
		SUPPORTED_LEVEL_STRING_TO_INT.put("FATAL", Integer.valueOf(FATAL));
		SUPPORTED_LEVEL_STRING_TO_INT.put("ERROR", Integer.valueOf(ERROR));
		SUPPORTED_LEVEL_STRING_TO_INT.put("WARN", Integer.valueOf(WARN));
		SUPPORTED_LEVEL_STRING_TO_INT.put("INFO", Integer.valueOf(INFO));
		SUPPORTED_LEVEL_STRING_TO_INT.put("DEBUG", Integer.valueOf(DEBUG));
		SUPPORTED_LEVEL_STRING_TO_INT.put("TRACE", Integer.valueOf(TRACE));

		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(OFF), "OFF");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(FATAL), "FATAL");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(ERROR), "ERROR");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(WARN), "WARN");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(INFO), "INFO");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(DEBUG), "DEBUG");
		SUPPORTED_LEVEL_INT_TO_STRING.put(Integer.valueOf(TRACE), "TRACE");
		// Set CXF logger to CommonLogger
		// org.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger
		// log4j.configuration=nma-logging.properties
		System.setProperty("org.apache.commons.logging.Log", CommonLogger.class.getName());
		// System.setProperty("log4j.configuration",CommonConstant.CONFIG_FILE_Common_LOGGER);
//        System.setProperty("org.apache.cxf.Logger", CommonLogger.CommonJdkLogger.class.getName());
		System.setProperty("org.mortbay.log.class", CommonLogger.class.getName());

//        try {
//            PropertyConfigurator.configureAndWatch(CommonConstants.CONFIG_FILE_Common_LOGGER, 30000);
//        } catch (Exception ex) {
//            PropertyConfigurator.configure(CommonLogger.class.getResource("/com/tellabs/Common/nma-logging.properties"));
//            ex.printStackTrace();
//        }
	}

	private static final Level getLevel(int levelInt) {
		Level level = Level.FINE;
		switch (levelInt) {
		case 800:
			level = Level.INFO;
			break;
		case 1000:
			level = Level.SEVERE;
			break;

		default:
			break;
		}
		return level;
	}

	static final Map<String, CommonLogger> NBI_LOGGER_MAP = new ConcurrentHashMap<String, CommonLogger>();
	private static final CommonLogger cxfLogger = getCommonLogger("org.apache.cxf");
	private static final CommonLogger jettyLogger = getCommonLogger("org.mortbay");
	private static final CommonLogger serverStatusLogger = getCommonLogger("com.tellabs.ServerStatus");
	private static final CommonLogger serverAuditLogger = getCommonLogger("com.tellabs.ServerAudit");
	private static final CommonLogger springLogger = getCommonLogger("org.springframework");
	private static final CommonLogger inmFmkLogger = getCommonLogger("tellabs.inmfwk.common");
	private static final CommonLogger nmaProfilingLogger = getCommonLogger(
			"com.tellabs.ManagerPlatform.nma.Util.timer");
	private static final CommonLogger soapRequestLogger = getCommonLogger(
			"com.tellabs.ManagerPlatform.nma.log.NMALoggingInInterceptor");
	private static final CommonLogger soapResponseLogger = getCommonLogger(
			"com.tellabs.ManagerPlatform.nma.log.NMALoggingOutInterceptor");

	/**************
	 * Jetty Loggers Begin
	 */
	public void setDebugEnabled(boolean enabled) {
		logger.setLevel(Level.FINER);
	}

	public void info(String msg, Object arg0, Object arg1) {
		log(CommonLogger.INFO, msg, arg0);
	}

	public void debug(String msg, Throwable th) {
		log(CommonLogger.DEBUG, msg, th);

	}

	public void debug(String msg, Object arg0, Object arg1) {
		logger.log(Level.FINER, msg);

	}

	public void warn(String msg, Object arg0, Object arg1) {
		logger.log(Level.WARNING, msg);

	}

	public void warn(String msg, Throwable th) {
		log(WARN, msg, th);
	}

	public CommonLogger getLogger(String className) {
		return getCommonLogger(className);
	}

	/**
	 * Jetty Loggers End
	 ***************************/

	/**************
	 * Apache Common Loggers Begin
	 */
	public boolean isTraceEnabled() {

		return logger.isLoggable(Level.FINEST);
	}

	public boolean isDebugEnabled() {

		return logger.isLoggable(Level.FINER);
	}

	public boolean isInfoEnabled() {

		return logger.isLoggable(Level.FINE);
	}

	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public boolean isFatalEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	public void trace(Object message) {
		log(TRACE, message);
	}

	public void trace(Object message, Throwable t) {
		log(TRACE, message, t);
	}

	public void debug(Object message) {
		log(DEBUG, message);
	}

	public void debug(Object message, Throwable t) {
		log(DEBUG, message, t);
	}

	public void info(Object message) {
		log(INFO, message);
	}

	public void info(Object message, Throwable t) {
		log(INFO, message, t);
	}

	public void warn(Object message) {
		log(WARN, message);
	}

	public void warn(Object message, Throwable t) {
		log(WARN, message, t);
	}

	public void error(Object message) {
		log(ERROR, message);
	}

	public void error(Object message, Throwable t) {
		log(ERROR, message, t);
	}

	public void fatal(Object message) {
		log(FATAL, message);
	}

	public void fatal(Object message, Throwable t) {
		log(FATAL, message, t);
	}

	/**
	 * Apache Common Loggers End
	 ***************************/

	/**
	 * Private constructor
	 * 
	 * @param level
	 */
	public CommonLogger() {
		logger = Logger.getLogger("org.mortbay");
	}

	public CommonLogger(String className) {

		logger = Logger.getLogger(className);
	}

	public static final CommonLogger getCommonLogger(String className) {

		if (NBI_LOGGER_MAP.get(className) != null) {
			return (CommonLogger) NBI_LOGGER_MAP.get(className);
		} else {
			CommonLogger nbiLogger = new CommonLogger(className);
			if (nLevel > -1) {
				nbiLogger.setLoggerLevel(nLevel);
			}
			NBI_LOGGER_MAP.put(className, nbiLogger);
			return nbiLogger;
		}
	}

	public static final CommonLogger getLog(Class<? extends Object> className) {

		return getCommonLogger(className.getName());
	}

	public static final CommonLogger getLogger(Class<? extends Object> className) {

		return getCommonLogger(className.getName());
	}

	public static final CommonLogger getCxfLogger() {
		return cxfLogger;
	}

	public static final CommonLogger getJettyLogger() {
		return jettyLogger;
	}

	public static final CommonLogger getServerStatusLogger() {
		return serverStatusLogger;
	}

	public static final CommonLogger getServerAuditLogger() {
		return serverAuditLogger;
	}

	public static final CommonLogger getSoapRequestLogger() {
		return soapRequestLogger;
	}

	public static final CommonLogger getSoapResponseLogger() {
		return soapResponseLogger;
	}

	public static final CommonLogger getNmaProfilingLogger() {
		return nmaProfilingLogger;
	}

	public static final CommonLogger getInmFmkLogger() {
		return inmFmkLogger;
	}

	public static final CommonLogger getSpringLogger() {
		return springLogger;
	}

	/**
	 * Check whether this Logger is enabled for the <code>DEBUG</code> Level.
	 * 
	 * This function is intended to lessen the computational cost of disabled log
	 * debug statements. If you check before logging, you will not incur the cost of
	 * parameter construction. So, the preferred way for logging debug messages is
	 * as follows.
	 * <p>
	 * if(logger.isDebugEnabled()) { logger.log(NbiLogger.DEBUG, "Common", "This is
	 * entry number: " + i ); }
	 * 
	 * @return boolean - <code>true</code> if this category is debug enabled,
	 *         <code>false</code> otherwise.
	 * 
	 */

	/**
	 * Log message without throwable
	 * 
	 * @param level   logging level defined in NbiLogger
	 * @param target  target area where logging happens, could be service name
	 * @param message logging message
	 */
	private void log(int level, Object target, Object msg, Throwable t, boolean important) {
		String message = String.valueOf(msg);
		if (important) {
			if (null == target) {
				message = " \n*************  " + message + " *************";
			} else {
				message = "[" + target + "] \n*************  " + message + " *************";
			}
		} else {
			if (null != target)
				message = "[" + target + "] " + message;
		}
		Level logLevel = getLevel(level);
		if (null == t) {
			logger.log(logLevel, message);
		} else {
			logger.log(logLevel, message, t);
		}
	}

	public void logImportant(int level, Object message) {
		log(level, null, message, null, true);
	}

	public void logImportant(int level, Object message, Throwable t) {
		log(level, null, message, t, true);
	}

	public void logImportant(int level, Object target, Object message) {
		log(level, target, message, null, true);
	}

	public void logImportant(int level, Object target, Object message, Throwable t) {
		log(level, target, message, t, true);
	}

	public void log(int level, Object target, Object message) {
		log(level, target, message, null, false);
	}

	public void log(int level, Object message) {
		log(level, null, message, null, false);
	}

	public void log(int level, Object message, Throwable t) {
		log(level, null, message, t, false);
	}

	public void log(int level, Object target, Object message, Throwable t) {
		log(level, target, message, t, false);
	}

	public void log(int level, Object classname, Object method, Object message, Throwable t) {
		log(level, null, classname + "." + method + "() " + message, t, true);
	}

	/**
	 * Set the repository wide (for all the loggers) Logging Level
	 * 
	 * @param level
	 */
	public void setLoggerLevel(int level) {
		if (this.logger != null) {
			this.logger.setLevel(getLevel(level));
		}
	}

	/**
	 * Translate level from integer to string
	 * 
	 * @param level
	 * @return
	 */
	public static final String getLoggerLevel(int level) {
		return SUPPORTED_LEVEL_INT_TO_STRING.get(level);
	}

	public static final void setLoggerLevelAll(int level) {
		nLevel = level;
		for (Map.Entry<String, CommonLogger> loggerEntry : NBI_LOGGER_MAP.entrySet()) {
			loggerEntry.getValue().setLoggerLevel(level);
		}
	}

	/**
	 * Get the logging level at root level
	 */
	public static final int getRootLoggerLevel() {
		Logger topLogger = Logger.getGlobal();
		return topLogger.getLevel().intValue();
	}

	public static final int getLoggerLevel(String levelName) {
		Integer levelNumber = SUPPORTED_LEVEL_STRING_TO_INT.get(levelName.toUpperCase());
		return levelNumber == null ? -1 : levelNumber.intValue();
	}

	/**
	 * Set logger level for given logger name
	 * 
	 * @param loggerName
	 * @param level
	 */
	public static final void setLoggerLevel(String loggerName, int level) {
		Logger logger = Logger.getLogger(loggerName);
		if (logger != null) {
			logger.setLevel(getLevel(level));
		}
	}

	public static final String listLoggers() {
		String defaultLoggerLevel = Logger.getGlobal().getLevel().toString();
		StringBuilder loggerCategory = new StringBuilder(NBI_LOGGER_MAP.keySet().size() * 40);
		boolean f1 = true;
		for (Entry<String, CommonLogger> entry : NBI_LOGGER_MAP.entrySet()) {
			if (f1) {
				f1 = false;
			} else {
				loggerCategory.append(",");
			}
			Level currentLoggerLevel = entry.getValue().logger.getLevel();
			loggerCategory.append(entry.getKey()).append("/")
					.append((SystemUtil.isNullOrBlank(currentLoggerLevel) ? defaultLoggerLevel : currentLoggerLevel));
		}
		return loggerCategory.toString();
	}

	public static final boolean existedLogger(String loggerName) {
		return NBI_LOGGER_MAP.keySet().contains(loggerName);
	}

}