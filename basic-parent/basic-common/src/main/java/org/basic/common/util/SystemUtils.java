package org.basic.common.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.basic.common.bean.CommonLogger;
import org.basic.common.util.BasicException.BasicExceptionSubType;
import org.basic.common.util.BasicException.BasicExceptionType;

public class SystemUtils {

	public enum NotificationType {
		ALARM, INV_OC, INV_OD, INV_ST, INV_AVC, TCA, HBT, SYNC
	}

	private static CommonLogger logger = CommonLogger.getLogger(SystemUtils.class);

	private final static ThreadLocal<Long> localID = new ThreadLocal<Long>() {

		AtomicLong id = new AtomicLong(0);

		@Override
		public Long get() {
			return id.incrementAndGet();
		}
	};

	public final static long getId() {

		return localID.get();
	}

	public final static String getNotificationId(String notificationType) {

		if (notificationType == null)
			return nextId() + "";
		else
			return notificationType + "_" + nextId();
	}

	public static Long nextId() {

		return localID.get();
		// int k = randomIndex;
		// long currentTime = System.currentTimeMillis();
		// // SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		// // String dateString = formatter.format(currentTime);
		// return currentTime + getRandom(k);
	}

	public static String getRandom(int i) {

		Random random = new Random();
		if (i == 0)
			return "";
		String str = "";
		for (int k = 0; k < i; k++) {
			str = str + random.nextInt(9);
		}
		return str;
	}

	public static final <T> void println(T t) {
		printMessage(t);
	}

	public final static String getExceptionStackTrace(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage()).append('\n');
		for (int i = 0; i < ste.length; i++) {
			sb.append("\tat ").append(ste[i].toString()).append('\n');
		}
		return sb.toString();
	}

	public static final <T> void printMessage(T t) {
		System.out.println(t);
	}

	public final static boolean isNullOrBlank(Object obj) {

		return (obj == null) || obj.toString().trim().equals("");
	}

	public final static boolean isNullOrBlank(java.util.Collection<? extends Object> obj) {

		return obj == null || obj.isEmpty();
	}

	/**
	 * If the parameter source is null or source.toString() is blank, it return the
	 * parameter defaultValue. Or else, it returns the source.toString();
	 * 
	 * @param source
	 * @param defaultValue
	 * @return
	 */
	public final static String nvl(Object source, String defaultValue) {

		if (isNullOrBlank(source)) {
			return defaultValue;
		}
		return source.toString();
	}

	public final static String nvl(Object source) {

		if (isNullOrBlank(source)) {
			return "";
		}
		return source.toString();
	}

	/**
	 * Assert if the parameter bool is true.<br>
	 * If the parameter bool is 'Y', 'YES', 'T', 'TRUE', '1', it will return the
	 * boolean value true; Or else, false.
	 * 
	 * @param bool
	 * @return
	 */
	public final static boolean isTrue(String bool) {

		return "TRUE".equalsIgnoreCase(bool) || "T".equalsIgnoreCase(bool) || "YES".equalsIgnoreCase(bool)
				|| "Y".equalsIgnoreCase(bool) || "1".equals(bool);
	}

	public final static void closeQuitely(java.lang.AutoCloseable... closables) {

		for (java.lang.AutoCloseable closable : closables) {
			if (closable != null) {
				try {
					closable.close();
				} catch (Exception e) {
					logger.log(CommonLogger.ERROR, "closeQuitely", "object close exception", e);
				}
			} else {
				logger.log(CommonLogger.INFO, "closeQuitely", "object to be closed is NULL");
			}
		}
	}

	public final static void closeQuitely(java.io.InputStream... inputStreams) {

		for (java.io.InputStream inputStream : inputStreams) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					logger.log(CommonLogger.ERROR, "closeQuitely", "object close exception", e);
				}
			} else {
				logger.log(CommonLogger.INFO, "closeQuitely", "object to be closed is NULL");
			}
		}
	}

	public final static void setSystemTime(Calendar calendar) throws BasicException {

		String osName = System.getProperty("os.name");
		String cmd = "";
		Process process = null;
		try {
			if (osName.matches("^(?i)Windows.*$")) {
				// Format: HH:mm:ss
				cmd = "  cmd /c time " + DateUtils.formatDate(calendar, "HH:mm:ss");
				process = Runtime.getRuntime().exec(cmd);
				// Format: yyyy-MM-dd
				cmd = " cmd /c date " + DateUtils.formatDate(calendar, "yyyy-MM-dd");
				process = Runtime.getRuntime().exec(cmd);
			} else if (osName.matches(".*Sun.*")) {
				// Format: date mmddHHMMYYYY.SS
				cmd = "date " + DateUtils.formatDate(calendar, "MMddHHmmyyyy.ss");
				process = Runtime.getRuntime().exec(cmd);
			} else {
				cmd = "  date -s " + DateUtils.formatDate(calendar, "yyyyMMdd");
				process = Runtime.getRuntime().exec(cmd);
				// Format: HH:mm:ss
				cmd = "  date -s " + DateUtils.formatDate(calendar, "HH:mm:ss");
				process = Runtime.getRuntime().exec(cmd);
			}
		} catch (IOException ex) {
			try {
				byte b[] = new byte[1024];
				int r = 0;
				while ((r = process.getErrorStream().read(b, 0, 1024)) > -1) {
					logger.log(CommonLogger.ERROR, new String(b, 0, r), ex);
				}
			} catch (Exception e) {
			}
			new BasicException(BasicExceptionType.INTERNAL_EXCEPTION, BasicExceptionSubType.INVALID_INPUT,
					"Failed to set the system time. please make sure EMS installler have privileges to update the OS system time",
					ex);
		}
	}

	/**
	 * Check if heartBeat is valid. valid heartBeat should between 0~60000
	 * millseconds
	 * 
	 * @param heartBeatString
	 * @throws BasicException
	 */
	public final static boolean checkHeartBeat(String heartBeatString) throws BasicException {

		int heartBeatInt = 0;
		try {
			heartBeatInt = Integer.parseInt(heartBeatString);
		} catch (Exception ex) {
			throw new BasicException(BasicExceptionType.INTERNAL_EXCEPTION,
					"heartBeat \"" + heartBeatString + "\" is invalid and should be integer");
		}
		if (heartBeatInt < 0 || heartBeatInt > 60000) {
			throw new BasicException(BasicExceptionType.INTERNAL_EXCEPTION, BasicExceptionSubType.INVALID_INPUT,
					"heartBeat \"" + heartBeatString
							+ "\" is invalid. heartBeat should be between 0~60000 (milliseconds)");
		}
		return true;
	}

	public final static BasicException handleException(Logger logger2, Exception e, String message) {

		logger2.log(Level.FINE, message, e);
		// e.printStackTrace();
		// logger2.log(BasicLogger.ERROR, "handleException", message, e);
		return new BasicException(message);
	}
}