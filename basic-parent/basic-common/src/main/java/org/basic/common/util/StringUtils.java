package org.basic.common.util;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private StringUtils() {
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

	/**
	 * Define which characters are invalid for name and displayName. Invalid
	 * characters: ; / ? : @ = & " < > # % { } | \ ' ^ ~ [ ] ` <blank>
	 */
	private static final Pattern INVALID_IDM_NAME_PATTERN = Pattern
			.compile(".*[;/?:@=&\\\"<>#%{}|\\\\'^~\\[\\]`\\s].*");
	private static char[] NUMBER_ARRAY = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static char[] LOWER_ALPHABET_ARRAY = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static char[] UPPER_ALPHABET_ARRAY = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private static char[] SPECIAL_ARRAY = new char[] { '~', '}', '|', '{', '`', '_', '^', ']', '\\', '[', '@', '?', '>',
			'=', '<', ';', ':', '/', '.', '-', ',', '+', '*', ')', '(', '\'', '&', '%', '$', '#', '"', '!' };
	private static char[] ALPHABET_ARRAY = new char[LOWER_ALPHABET_ARRAY.length + UPPER_ALPHABET_ARRAY.length];
	private static char[] CHARACTER_ARRAY = new char[NUMBER_ARRAY.length + LOWER_ALPHABET_ARRAY.length
			+ UPPER_ALPHABET_ARRAY.length + SPECIAL_ARRAY.length];
	private static char[] CHARACTER_WITHOUT_ARRAY = new char[NUMBER_ARRAY.length + LOWER_ALPHABET_ARRAY.length
			+ UPPER_ALPHABET_ARRAY.length];
	private static SecureRandom RANDOM_GENERATOR = new SecureRandom();
	static {
		RANDOM_GENERATOR.setSeed(RANDOM_GENERATOR.generateSeed(20));

		int nextStart = 0;
		System.arraycopy(LOWER_ALPHABET_ARRAY, 0, ALPHABET_ARRAY, nextStart, LOWER_ALPHABET_ARRAY.length);
		nextStart += LOWER_ALPHABET_ARRAY.length;
		System.arraycopy(UPPER_ALPHABET_ARRAY, 0, ALPHABET_ARRAY, nextStart, UPPER_ALPHABET_ARRAY.length);

		nextStart = 0;
		System.arraycopy(NUMBER_ARRAY, 0, CHARACTER_WITHOUT_ARRAY, 0, NUMBER_ARRAY.length);
		nextStart += NUMBER_ARRAY.length;
		System.arraycopy(LOWER_ALPHABET_ARRAY, 0, CHARACTER_WITHOUT_ARRAY, nextStart, LOWER_ALPHABET_ARRAY.length);
		nextStart += LOWER_ALPHABET_ARRAY.length;
		System.arraycopy(UPPER_ALPHABET_ARRAY, 0, CHARACTER_WITHOUT_ARRAY, nextStart, UPPER_ALPHABET_ARRAY.length);

		nextStart += UPPER_ALPHABET_ARRAY.length;
		System.arraycopy(CHARACTER_WITHOUT_ARRAY, 0, CHARACTER_ARRAY, 0, CHARACTER_WITHOUT_ARRAY.length);
		System.arraycopy(SPECIAL_ARRAY, 0, CHARACTER_ARRAY, nextStart, SPECIAL_ARRAY.length);
	}

	/**
	 * Determines whether the given {@link CharSequence} is not {@code null} and is
	 * not empty (has length greater than zero). A {@link CharSequence} that
	 * contains only whitespace returns {@code true}. {@code
	 * StringUtils.hasLength(null) --> false
	 * StringUtils.hasLength("") --> false
	 * StringUtils.hasLength(" ") --> true
	 * StringUtils.hasLength(" \n \t") --> true
	 * StringUtils.hasLength("text") --> true
	 * StringUtils.hasLength(" text ") --> true
	 * }
	 *
	 * @param str the {@code CharSequence} to test for length. May be {@code null}.
	 *
	 * @return {@code true} if {@code str} is not {@code null} and has length
	 *         greater than {@code 0}; {@code false} otherwise.
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Determines whether the given {@link String} is not {@code null} and is not
	 * empty (has length greater than zero). A {@link String} that contains only
	 * whitespace returns {@code true}. {@code
	 * StringUtils.hasLength(null) --> false
	 * StringUtils.hasLength("") --> false
	 * StringUtils.hasLength(" ") --> true
	 * StringUtils.hasLength(" \n \t") --> true
	 * StringUtils.hasLength("text") --> true
	 * StringUtils.hasLength(" text ") --> true
	 * }
	 *
	 * @param str the {@code String} to test for length. May be {@code null}.
	 *
	 * @return {@code true} if {@code str} is not {@code null} and has length
	 *         greater than {@code 0}; {@code false} otherwise.
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * Determine whether the given {@link CharSequence} has text, that is, that it
	 * is not {@code null}, is not empty (has length greater than {@code 0}), and
	 * contains at least one non-whitespace character. {@code
	 * StringUtils.hasText(null) --> false
	 * StringUtils.hasText("") --> false
	 * StringUtils.hasText(" ") --> false
	 * StringUtils.hasText(" \n \t") --> false
	 * StringUtils.hasText("text") --> true
	 * StringUtils.hasText(" text ") --> true
	 * }
	 *
	 * @param str the {@code CharSequence} to test for the presence of text. May be
	 *            {@code null}.
	 *
	 * @return {@code true} if {@code str} is not {@code null}, not empty, and
	 *         contains at least one non-whitespace character; {@code false}
	 *         otherwise.
	 */
	public static boolean hasText(CharSequence str) {
		// Verify not null and not empty
		if (!hasLength(str)) {
			return false;
		}

		// Look for non-whitespace characters
		int length = str.length();
		for (int i = 0; i < length; ++i) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Determines whether the given {@link String} has text, that is, that it is not
	 * {@code null}, not empty (has length greater than zero), and contains at least
	 * one non-whitespace character.
	 *
	 * @param str the {@link String} to test for the presence of text. May be
	 *            {@code null}.
	 *
	 * @return {@code true} if {@code str} is not {@code null}, not empty, and
	 *         contains at least one non-whitespace character; {@code false}
	 *         otherwise.
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * Joins the given array or variable number of String objects into a single
	 * string with components delimited by the given separator.
	 *
	 * @param separator the text to insert between each string.
	 * @param args      the strings to join
	 *
	 * @return the joined string
	 */
	public static String join(String separator, Object... args) {
		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (Object arg : args) {
			if (!firstTime) {
				builder.append(separator);
			}
			builder.append(arg.toString());
			firstTime = false;
		}

		return builder.toString();
	}

	/**
	 * Determines string equality in constant time for inputs of the same length.
	 * This method is used to defeat timing attacks, which use typical optimizations
	 * to return as soon as a difference between the strings is discovered to
	 * reverse engineer passwords.
	 *
	 * @param lhs the left side string
	 * @param rhs the right side string
	 *
	 * @return {@code true} if the input strings are equal; {@code false} otherwise.
	 */
	public static boolean secureStringEquals(String lhs, String rhs) {
		// Keep updating the result code to keep the compiler from attempting to be
		// "helpful" and optimize this
		// method, removing its time invariance.
		int result = 0;
		if (lhs.length() != rhs.length()) {
			// Safe to return immediately since there's no issue with timing
			return false;
		}
		if (lhs.length() == 0) {
			// Safe to return immediately since there's no issue with timing
			return true;
		}

		for (int i = 0; i < lhs.length(); ++i) {
			char leftChar = lhs.charAt(i);
			char rightChar = rhs.charAt(i);
			result |= leftChar ^ rightChar;
		}

		return (result == 0);
	}

	/**
	 * Converts text to a string that can be used in a URL:
	 *
	 * <ul>
	 * <li>Decomposes characters with diacritic marks into their latin
	 * equivalents</li>
	 * <li>Converts alpha characters to lower case</li>
	 * <li>Converts any sequences of non-alphanumeric characters into a single
	 * hyphen</li>
	 * <li>Strips any leading or trailing hyphen</li>
	 * </ul>
	 *
	 * @param text the original text
	 *
	 * @return the normalized text
	 */
	public static String normalizeText(String text) {
		// To upper case in order to preserve things like German sharp-s to SS
		String normalized = text.toUpperCase();
		// Convert diacritical marks
		normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
		// Remove superfluous diacritical characters
		normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		// replace non-alphanumeric characters sequences with a single hyphen
		normalized = normalized.replaceAll("[^\\p{Alnum}]+", "-");
		// Remove trailing hyphen
		normalized = normalized.replaceAll("-*$", "");
		// Remove leading hyphen
		normalized = normalized.replaceAll("^-*", "");
		// All lower case
		return normalized.toLowerCase();
	}

	/**
	 * Judges if one string means true.
	 * 
	 * @param obj boolean value
	 * @return return if strValue is true/yes/enabled. otherwise it will retured as
	 *         false.
	 */
	public static boolean isTrue(Object obj) {
		String strValue = String.valueOf(obj);
		return ("true".equalsIgnoreCase(strValue) || "yes".equalsIgnoreCase(strValue)
				|| "enabled".equalsIgnoreCase(strValue));
	}

	/**
	 * Judges if one string is digital.
	 * 
	 * @param strValue boolean value
	 * @return return true if strValue is digital. otherwise it will retured as
	 *         false.
	 */
	public static final Pattern NUMERIC_PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");

	public static boolean isNumeric(String strValue) {
		return NUMERIC_PATTERN.matcher(strValue).matches();
	}

	/**
	 * Trims and returns the trimmed string, or an empty string if strValue is null.
	 *
	 * @param strValue the input string value
	 * @return return the trimmed string, or an empty string, but never null
	 */
	public static String trimToEmpty(String strValue) {
		if (strValue != null) {
			return strValue.trim();
		}
		return "";
	}

	static final Map<String, Pattern> CACHED_REGEX_PATTERN = new ConcurrentHashMap<String, Pattern>();

	/**
	 * Check if {@code inputString} matches the complexity of string
	 * 
	 * @param containDigit        if inputString contains at least one digit
	 * @param containLowerAlphbet if inputString contains at least one lower-case
	 *                            alphbet
	 * @param containUpperAlphbet if inputString contains at least one upper-case
	 *                            alphbet
	 * @param containSpecial      if inputString contains at least one specifical
	 *                            character except digit and alphbet
	 * @param minLength           what the minimum size of inputString is
	 * @param inputString         literal which will be valiadated
	 * @return if complexity is satisfied, it return true. otherwise, false
	 */
	public static final boolean checkStringComplexity(boolean containDigit, boolean containLowerAlphbet,
			boolean containUpperAlphbet, boolean containAlphbet, boolean containSpecial, int minLength,
			String inputString) {
		String patternKey = containDigit + "_" + containLowerAlphbet + "_" + containUpperAlphbet + "_" + containAlphbet
				+ containSpecial + "_" + minLength;
		Pattern pattern = CACHED_REGEX_PATTERN.get(patternKey);
		if (null == pattern) {
			StringBuilder regexpBuilder = new StringBuilder("^");
			if (containDigit) {
				regexpBuilder.append("(?=.*\\d)");
			}
			if (containLowerAlphbet) {
				regexpBuilder.append("(?=.*[a-z])");
			}
			if (containUpperAlphbet) {
				regexpBuilder.append("(?=.*[A-Z])");
			}
			if (containAlphbet) {
				regexpBuilder.append("(?=.*[a-zA-Z])");
			}
			if (containSpecial) {
				regexpBuilder.append("(?=.*[^\\p{Alnum}])");
			}
			regexpBuilder.append(".{").append(Math.max(0, minLength)).append(",}$");
			pattern = Pattern.compile(regexpBuilder.toString());
			CACHED_REGEX_PATTERN.put(patternKey, pattern);
		}
		return pattern.matcher(inputString).matches();
	}

	public static final String POSITIONAL_PLACEHOLDER = "{}";

	/**
	 * Formats one message based specified postional parameter. positional
	 * placeholder is {@link #POSITIONAL_PLACEHOLDER}<br>
	 * 
	 * if the number of {@code args} is larger than positional parameter, only
	 * preceeding position parameter will be replaced sequentially. remain
	 * {@code args} will be rejected<br>
	 * if the number of {@code args} is less than positional parameter, only
	 * preceeding position parameter will be replaced sequentially. remain
	 * positional parameter will be kept without replacement<br>
	 * for example.<br>
	 * <li>{@link #formatMessage("hello, name={}, word={}","world", "bye")}<br>
	 * Output: hello, name=world, word=bye
	 * <li>{@link #formatMessage("hello, name={}, word={}","world")}<br>
	 * Output: hello, name=world, word={}
	 * <li>{@link #formatMessage("hello, name={}, word={}","world", "bye",
	 * "12")}<br>
	 * Output: hello, name=world, word=bye
	 * 
	 * @param msg  Object to be formatted. if msg is null, output is "null".
	 *             otherwise, String.valueOf(msg) will be called to construct the
	 *             message pattern.
	 * @param args positionl parameter
	 * @return formatted string
	 */
	public static final <T> String formatMessage(T msg, Object... args) {
		String msgFormat = String.valueOf(msg);
		if (null == args || msg == null) {
			return msgFormat;
		} else {
			int start = -1;
			StringBuilder output = new StringBuilder();
			int pos = -1;
			while ((start = msgFormat.indexOf(POSITIONAL_PLACEHOLDER)) != -1) {
				pos++;
				output.append(msgFormat.substring(0, start));
				if (pos < args.length) {
					output.append(args[pos]);
				}
				if (pos == args.length) {
					output.append(msgFormat);
				} else {
					msgFormat = msgFormat.substring(start + POSITIONAL_PLACEHOLDER.length());
				}
			}
			output.append(msgFormat);
			return output.toString();
		}
	}

	/**
	 * Currently, the Organization name and the DatabaseUser name must NOT contain
	 * the invalid chars defined in INVALID_IDM_NAME_PATTERN.
	 * 
	 * @param name the input name
	 * @return true if the intpu name does not contain the invalid chars
	 */
	public static boolean isNameValid(String name) {
		return !INVALID_IDM_NAME_PATTERN.matcher(name).matches();
	}

	/**
	 * Check if string is boolean
	 * 
	 * @param value to be checked
	 */
	public static boolean isBoolean(String value) {

		Pattern queryLangPattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
		Matcher matcher = queryLangPattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * Check if string is Integer
	 * 
	 * @param value to be checked
	 */
	public static boolean isInteger(String value) {
		final Pattern NUMERIC_PATTERN = Pattern.compile("^[1-9]\\d*|0$");
		return NUMERIC_PATTERN.matcher(value).matches();
	}

	/**
	 * Generate one random letters.
	 * 
	 * @param containDigit        indicates at least one digit should be contained
	 * @param containLowerAlphbet indicates at least one upper lower alphabet should
	 *                            be contained
	 * @param containUpperAlphbet indicates at least one upper alphabet digit should
	 *                            be contained
	 * @param containAlphbet      indicates at least one alphabet should be
	 *                            contained
	 * @param containSpecial      indicates at least one special characters should
	 *                            be contained
	 * @param randomLength        lenght of randomn letters
	 * @return random letters with length = {@code randomLength}
	 */
	public static String generateRandom(boolean containDigit, boolean containLowerAlphbet, boolean containUpperAlphbet,
			boolean containAlphbet, boolean containSpecial, int randomLength) {
		int minLength = 0;

		char[] randomLetters = new char[randomLength];
		if (containDigit) {
			randomLetters[minLength] = NUMBER_ARRAY[RANDOM_GENERATOR.nextInt(NUMBER_ARRAY.length)];
			++minLength;
		}
		if (containLowerAlphbet) {
			randomLetters[minLength] = LOWER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(LOWER_ALPHABET_ARRAY.length)];
			++minLength;
		}
		if (containUpperAlphbet) {
			randomLetters[minLength] = UPPER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(UPPER_ALPHABET_ARRAY.length)];
			++minLength;
		}
		if (containAlphbet && (!containLowerAlphbet && !containUpperAlphbet)) {
			randomLetters[minLength] = UPPER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(UPPER_ALPHABET_ARRAY.length)];
			++minLength;
		}
		if (containSpecial) {
			randomLetters[minLength] = SPECIAL_ARRAY[RANDOM_GENERATOR.nextInt(SPECIAL_ARRAY.length)];
			++minLength;
		}

		if (randomLength < minLength) {
			throw new IllegalArgumentException("randomlength should not smaller than: " + minLength);
		}

		for (int i = minLength; i < randomLength; i++) {
			randomLetters[i] = CHARACTER_ARRAY[RANDOM_GENERATOR.nextInt(CHARACTER_ARRAY.length)];
		}

		for (int i = 0; i < randomLength; i++) {
			int r = i + RANDOM_GENERATOR.nextInt(randomLength - i);
			char temp = randomLetters[i];
			randomLetters[i] = randomLetters[r];
			randomLetters[r] = temp;
		}

		return new String(randomLetters);
	}

	/**
	 * Generate one random letters which contains only the digits or alphabet.
	 * 
	 * @param containDigit        indicates at least one digit should be contained
	 * @param containLowerAlphbet indicates at least one upper lower alphabet should
	 *                            be contained
	 * @param containUpperAlphbet indicates at least one upper alphabet digit should
	 *                            be contained
	 * @param containAlphbet      indicates at least one alphabet should be
	 *                            contained
	 * @param containSpecial      indicates at least one special characters should
	 *                            be contained
	 * @param randomLength        lenght of randomn letters
	 * @return random letters with length = {@code randomLength}
	 */
	public static String generateRandomWithoutSpecial(boolean containDigit, boolean containLowerAlphbet,
			boolean containUpperAlphbet, boolean containAlphbet, int randomLength) {
		int minLength = 0;

		char[] randomLetters = new char[randomLength];
		if (containDigit) {
			randomLetters[minLength] = NUMBER_ARRAY[RANDOM_GENERATOR.nextInt(NUMBER_ARRAY.length)];
			++minLength;
		}
		if (containLowerAlphbet) {
			randomLetters[minLength] = LOWER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(LOWER_ALPHABET_ARRAY.length)];
			++minLength;
		}
		if (containUpperAlphbet) {
			randomLetters[minLength] = UPPER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(UPPER_ALPHABET_ARRAY.length)];
			++minLength;
		}
		if (containAlphbet && (!containLowerAlphbet && !containUpperAlphbet)) {
			randomLetters[minLength] = UPPER_ALPHABET_ARRAY[RANDOM_GENERATOR.nextInt(UPPER_ALPHABET_ARRAY.length)];
			++minLength;
		}

		if (randomLength < minLength) {
			throw new IllegalArgumentException("randomlength should not smaller than: " + minLength);
		}

		for (int i = minLength; i < randomLength; i++) {
			randomLetters[i] = CHARACTER_WITHOUT_ARRAY[RANDOM_GENERATOR.nextInt(CHARACTER_WITHOUT_ARRAY.length)];
		}

		for (int i = 0; i < randomLength; i++) {
			int r = i + RANDOM_GENERATOR.nextInt(randomLength - i);
			char temp = randomLetters[i];
			randomLetters[i] = randomLetters[r];
			randomLetters[r] = temp;
		}
		return new String(randomLetters);
	}

	public static boolean isNullOrBlank(String value) {
		return value == null || value.length() == 0;
	}

}
