package org.basic.db.paging.sybase.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String upperCaseFirstChar(String iString) {
			String newString;
			newString = iString.substring(0, 1).toUpperCase()
					+ iString.substring(1);
			return newString;
	}
}
