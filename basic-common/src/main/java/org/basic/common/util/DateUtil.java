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

/**
 * 
 */
public class DateUtil {

    private final static CommonLogger logger = CommonLogger.getLogger(DateUtil.class);
    private final static SimpleDateFormat DATE_FORMATTER = (SimpleDateFormat) SimpleDateFormat.getInstance();

    public final static String timestampFormatForDbQuery(String timeValue) {

        return " to_timestamp('" + formatDate(parseItuDate(timeValue)) + "', '" + DB_TIMESTAMP_FORMAT
                + "') ";
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
            logger.log(CommonLogger.SEVERE, "parseDate", e.getMessage(), e);
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
            logger.log(CommonLogger.SEVERE, "parseDate", e.getMessage(), e);
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
            logger.log(CommonLogger.DEBUG, "getTimeStamp", "Translate time to Time_T format Error");
        }
        return 0;
    }
}
