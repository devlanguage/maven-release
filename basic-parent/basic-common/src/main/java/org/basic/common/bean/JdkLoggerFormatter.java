package org.basic.common.bean;

import java.util.logging.LogRecord;

import org.basic.common.util.DateUtils;

public class JdkLoggerFormatter extends java.util.logging.Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder output = new StringBuilder(512);
        Throwable t;
        output.append(DateUtils.formatDate(System.currentTimeMillis()) + " - " + record.getLoggerName() + "." + record.getSourceMethodName()
                + " - " + record.getMessage() + "\n");
        if ((t = record.getThrown()) != null) {
            StackTraceElement[] trace = t.getStackTrace();
            for (StackTraceElement traceElement : trace)
                output.append("\tat " + traceElement).append("\n");
        }
        return output.toString();
    }
}
