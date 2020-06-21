package org.basic.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Logger with "standard" output and error output stream.
 *
 * @author jdcasey
 */
public class SystemLogUtils {
	public static final void debug(CharSequence content) {
		print("debug", content);
	}

	public static final void debug(CharSequence content, Throwable error) {
		print("debug", content, error);
	}

	public static final void debug(Throwable error) {
		print("debug", error);
	}

	public static final void info(CharSequence content) {
		print("info", content);
	}

	public static final void info(CharSequence content, Throwable error) {
		print("info", content, error);
	}

	public static final void info(Throwable error) {
		print("info", error);
	}

	public static final void warn(CharSequence content) {
		print("warn", content);
	}

	public static final void warn(CharSequence content, Throwable error) {
		print("warn", content, error);
	}

	public static final void warn(Throwable error) {
		print("warn", error);
	}

	public static final void error(CharSequence content) {
		System.err.println("[error] " + content.toString());
	}

	public static final void error(CharSequence content, Throwable error) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);

		error.printStackTrace(pWriter);

		System.err.println("[error] " + content.toString() + "\n\n" + sWriter.toString());
	}

	public static final void error(Throwable error) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);

		error.printStackTrace(pWriter);

		System.err.println("[error] " + sWriter.toString());
	}

	public boolean isDebugEnabled() {
		return false;
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public boolean isErrorEnabled() {
		return true;
	}

	private static final void print(String prefix, CharSequence content) {
		System.out.println("[" + prefix + "] " + content.toString());
	}

	private static final void print(String prefix, Throwable error) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);

		error.printStackTrace(pWriter);

		System.out.println("[" + prefix + "] " + sWriter.toString());
	}

	private static final void print(String prefix, CharSequence content, Throwable error) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);

		error.printStackTrace(pWriter);

		System.out.println("[" + prefix + "] " + content.toString() + "\n\n" + sWriter.toString());
	}
}