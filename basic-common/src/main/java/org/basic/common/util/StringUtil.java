package org.basic.common.util;

public class StringUtil {
	private StringUtil() {
	}

	/**
	 * Finds out if the given character sequence starts with a whitespace character.
	 *
	 * @return {@code true} if the given character sequence is not empty and starts
	 *         with a whitespace character; {@code false} otherwise
	 * @exception NullPointerException if the given character sequence is
	 *                                 {@code null}
	 */
	public static boolean startsWithWhitespace(final CharSequence charSeq) {
		if (charSeq.length() == 0) {
			return false;
		}
		return Character.isWhitespace(charSeq.charAt(0));
	}

	/**
	 * Finds out if the given character sequence ends with a whitespace character.
	 *
	 * @return {@code true} if the given character sequence is not empty and ends
	 *         with a whitespace character; {@code false} otherwise
	 * @exception NullPointerException if the given character sequence is
	 *                                 {@code null}
	 */
	public static boolean endsWithWhitespace(final CharSequence charSeq) {
		if (charSeq.length() == 0) {
			return false;
		}
		return Character.isWhitespace(charSeq.charAt(charSeq.length() - 1));
	}

	public static String decapitalize(String name) {
		if ((name == null) || (name.length() == 0)) {
			return name;
		}
		if ((name.length() > 1) && (Character.isUpperCase(name.charAt(1))) && (Character.isUpperCase(name.charAt(0)))) {
			return name;
		}
		char[] chars = name.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	public static String capitalize(String name) {
		if ((name == null) || (name.length() == 0)) {
			return name;
		}
		char[] chars = name.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}


	public static String toString(byte[] uncompress) {
		return new String(uncompress, CommonUtils.UTF8_CHARSET);
	}

}
