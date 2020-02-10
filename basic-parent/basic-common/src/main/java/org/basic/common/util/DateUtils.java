package org.basic.common.util;

import static org.basic.common.bean.CommonConstants.DB_TIMESTAMP_FORMAT;
import static org.basic.common.bean.CommonConstants.DEFAULT_DATE_FORMAT;
import static org.basic.common.bean.CommonConstants.ITU_DATE_FORMAT_NO_TIME_ZONE;
import static org.basic.common.bean.CommonConstants.ITU_DATE_FORMAT_WITH_TIME_ZONE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.basic.common.bean.CommonLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class DateUtils {

	private final static SimpleDateFormat DATE_FORMATTER = (SimpleDateFormat) SimpleDateFormat.getInstance();
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
//  //2018-09-17T09:30:32.026+0000
//    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//    2018-09-17T09:31:30.188Z
//    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//  2018-09-17T09:32:42.085
//  DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");    
//    2018-09-17T17:35:54.428CST
//    DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
	public static final String UTC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static ThreadLocal<SimpleDateFormat> UTC_CACHE = new ThreadLocal<SimpleDateFormat>();
	private static ThreadLocal<SimpleDateFormat> DEFAULT_CACHE = new ThreadLocal<SimpleDateFormat>();

	public static void main(String[] args) {
		Date today = new Date();
		System.out.println(getUtcSimpleDataFormat().format(today));
	}

	private static final SimpleDateFormat getUtcSimpleDataFormat() {
		SimpleDateFormat utcDateFormat = UTC_CACHE.get();
		if (null == utcDateFormat) {
			utcDateFormat = new SimpleDateFormat(UTC_DATE_PATTERN);
//            utcDateFormat.setTimeZone(CommonUtils.UTC_ZONE);
			UTC_CACHE.set(utcDateFormat);
		}
		return utcDateFormat;
	}

	private static final SimpleDateFormat getDefaultSimpleDataFormat() {
		SimpleDateFormat defaultDateFormat = DEFAULT_CACHE.get();
		if (null == defaultDateFormat) {
			defaultDateFormat = new SimpleDateFormat(UTC_DATE_PATTERN);
			DEFAULT_CACHE.set(defaultDateFormat);
		}
		return defaultDateFormat;
	}

	/**
	 * Format one Date into date/time string as specified date pattern
	 * 
	 * @param date    time value to be formatted
	 * @param pattern specify how date will be formatted
	 * @return formatted time string
	 */
	public static final String format(Date date, String pattern) {
		return StringUtils.hasText(pattern) ? new SimpleDateFormat(pattern).format(date)
				: getDefaultSimpleDataFormat().format(date);
	}

	/**
	 * Format one Date into date/time string as UTC date pattern without Date locale
	 * convertion
	 * 
	 * @param date time value to be formatted
	 * @return formatted time string
	 */
	public static final String format(Date date) {
		return getDefaultSimpleDataFormat().format(date);
	}

	/**
	 * Return the current time as UTC Time
	 * 
	 * @return current time
	 */
	public static final Date getTimeInUtc() {
		return new Date(getCalendarNowInUtc().getTimeInMillis());
	}

	/**
	 * Return the current time as UTC Time
	 * 
	 * @return current time
	 */
	public static final Date getTimeNow() {
		return new Date();
	}

	/**
	 * Parse the localTime in UTC format
	 * 
	 * @param localTime local time
	 * @return
	 */
	public static final Date parseTime(String localTime) {
		try {
			return getDefaultSimpleDataFormat().parse(localTime);
		} catch (ParseException e) {
			logger.error("Failed to parse date: {}", localTime, e);
			return null;
		}
	}

	/**
	 * Parse the UTC Time in string to local time as java {@link java.util.Date}
	 * 
	 * @param utcTime UTC Time
	 * @return
	 */
	public static final Date parseUtcTime(String utcTime) {
		try {
			return getUtcSimpleDataFormat().parse(utcTime);
		} catch (ParseException e) {
			logger.error("Failed to parse date: {}", utcTime, e);
			return null;
		}
	}

	/**
	 * Return current time as {@link Calendar}
	 * 
	 * @return
	 */
	public static final Calendar getCalendarNowInUtc() {
		Calendar now = Calendar.getInstance();
		int zoneOffset = now.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = now.get(java.util.Calendar.DST_OFFSET);
		now.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return now;
	}

	/**
	 * Return current time as {@link Calendar}
	 * 
	 * @return
	 */
	public static final Calendar getCalendarNow() {
		Calendar now = Calendar.getInstance();
		return now;
	}

	/**
	 * Returns if time represented by {@link when} is before current time
	 * 
	 * @param when {@code Object} to compare
	 * @return true if time represented by when is before current time. Otherwise,
	 *         returns false
	 * 
	 */
	public static boolean isBeforeNow(Date when) {
		return when.getTime() < getTimeNow().getTime();
	}

	/**
	 * Returns if time represent by parameter when is after current time
	 * 
	 * @param when {@code Object} to compare
	 * @return true if time represented by when is after current time. Otherwise,
	 *         returns false
	 */
	public static boolean isAfterNow(Date when) {
		return when.getTime() > getTimeNow().getTime();
	}

	/**
	 * Get the number of milliseconds by the minutes
	 * 
	 * @param minute the amount of minutes to convert
	 * @return
	 */
	public static final long minutes(long minute) {
		return 1000 * 60 * minute;
	}

	/**
	 * Get the number of milliseconds by the hours
	 * 
	 * @param hour the amount of hour to convert
	 * @return
	 */
	public static final long hours(long hour) {
		return minutes(hour * 60);
	}

	/**
	 * Returns a copy of the {@link java.util.Date} plus number of addMinutes
	 * 
	 * @param src        one {@link Date} to be added
	 * @param addMinutes the amount of minutes to be added
	 * @return new {@link Date} plus addMinutes
	 */
	public static Date addMinutes(Date src, int addMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.MINUTE, addMinutes);
		return calendar.getTime();
	}

	/**
	 * Returns a copy of the {@link java.util.Date} plus number of addHours
	 * 
	 * @param src      one {@link Date} to be added
	 * @param addHours the amount of hours to be added
	 * @return new {@link Date} plus addMinutes
	 */
	public static Date addHours(Date src, int addHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.HOUR, addHours);
		return calendar.getTime();
	}

	public final static String timestampFormatForDbQuery(String timeValue) {

		return " to_timestamp('" + formatDate(parseItuDate(timeValue)) + "', '" + DB_TIMESTAMP_FORMAT + "') ";
	}

	public final static String formatDate(long date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return formatDate(calendar, DEFAULT_DATE_FORMAT);
	}

	public final static String formatDate(Date date) {

		return formatDate(date, DEFAULT_DATE_FORMAT);
	}

	public final static String formatDate(Date date, String pattern) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return formatDate(calendar, pattern);

	}

	public final static String formatDate(Calendar calendar) {

		return formatDate(calendar, DEFAULT_DATE_FORMAT);
	}

	// 2011-03-18T05:42:54.000-05:00
	public final static String formatDate(Calendar calendar, String pattern) {

		DATE_FORMATTER.applyPattern(pattern);
		DATE_FORMATTER.setTimeZone(calendar.getTimeZone());
		return DATE_FORMATTER.format(calendar.getTime());
	}

	public final static Date parseDate(String source) {

		return parseDate(source, DEFAULT_DATE_FORMAT);
	}

	public final static Date parseDate(String soure, String pattern) {

		try {
			DATE_FORMATTER.applyPattern(pattern);
			return DATE_FORMATTER.parse(soure);
		} catch (ParseException e) {
			logger.error("parseDate", e);
			return null;
		}
	}

	public final static Date parseItuDate(String soure) {

		try {
			if (soure.contains("+") || soure.contains("-")) {
				DATE_FORMATTER.applyPattern(ITU_DATE_FORMAT_WITH_TIME_ZONE);
			} else {
				DATE_FORMATTER.applyPattern(ITU_DATE_FORMAT_NO_TIME_ZONE);
			}
			return DATE_FORMATTER.parse(soure);
		} catch (Exception e) {
			logger.error("parseDate", e);
			return null;
		}
	}

	public final static Calendar parseItuCalendar(String soure) {

		Calendar now = Calendar.getInstance();
		now.setTime(parseItuDate(soure));
		return now;
	}

	public final static String currentTime() {

		Date now = new Date(System.currentTimeMillis());
		return formatDate(now);
	}

	public static long getTime(String time, String timePattern) {

		// String timePattern = "yyyyMMddHHmmss.SSS";
		String time_t = time;
		try {
			if (time_t != null && !time_t.equals("")) {
				SimpleDateFormat formtTime = new SimpleDateFormat(timePattern);
				Date date = formtTime.parse(time_t);
				return date.getTime();
			}
		} catch (Exception ex) {
			logger.error("getTimeStamp", "Translate time to Time_T format Error");
		}
		return 0;
	}
}
