package org.basic.common.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private StringUtils() {
	}

	// Joining
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No separator is added to the joined String. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null)            = null
	 * StringUtils.join([])              = ""
	 * StringUtils.join([null])          = ""
	 * StringUtils.join(["a", "b", "c"]) = "abc"
	 * StringUtils.join([null, "", "a"]) = "a"
	 * </pre>
	 *
	 * @param          <T> the specific type of values to join together
	 * @param elements the values to join together, may be null
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 * @since 3.0 Changed signature to use varargs
	 */
	@SafeVarargs
	public static <T> String join(final T... elements) {
		return join(elements, null);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 */
	public static String join(final Object[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final long[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final int[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final short[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final byte[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final char[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final float[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final double[] array, final char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 */
	public static String join(final Object[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final long[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final int[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final byte[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final short[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final char[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final double[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
	 * StringUtils.join([1, 2, 3], null) = "123"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the array
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 3.2
	 */
	public static String join(final float[] array, final char separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final StringBuilder buf = new StringBuilder(noOfItems);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator is
	 * the same as an empty String (""). Null objects or empty strings within the
	 * array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 *
	 * @param array     the array of values to join together, may be null
	 * @param separator the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null array input
	 */
	public static String join(final Object[] array, final String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing the
	 * provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator is
	 * the same as an empty String (""). Null objects or empty strings within the
	 * array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *, *, *)                = null
	 * StringUtils.join([], *, *, *)                  = ""
	 * StringUtils.join([null], *, *, *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
	 * StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
	 * StringUtils.join(["a", "b", "c"], "--", 2, 2)  = ""
	 * StringUtils.join(["a", "b", "c"], null, 0, 3)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "", 0, 3)    = "abc"
	 * StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
	 * </pre>
	 *
	 * @param array      the array of values to join together, may be null
	 * @param separator  the separator character to use, null treated as ""
	 * @param startIndex the first index to start joining from.
	 * @param endIndex   the index to stop joining from (exclusive).
	 * @return the joined String, {@code null} if null array input; or the empty
	 *         string if {@code endIndex - startIndex <= 0}. The number of joined
	 *         entries is given by {@code endIndex - startIndex}
	 * @throws ArrayIndexOutOfBoundsException ife<br>
	 *                                        {@code startIndex < 0} or <br>
	 *                                        {@code startIndex >= array.length()}
	 *                                        or <br>
	 *                                        {@code endIndex < 0} or <br>
	 *                                        {@code endIndex > array.length()}
	 */
	public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}

		final StringBuilder buf = new StringBuilder(noOfItems);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterator  the {@code Iterator} of values to join together, may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.0
	 */
	private static final int STRING_BUILDER_SIZE = 256;// Count matches
	// -----------------------------------------------------------------------

	/**
	 * <p>
	 * Counts how many times the substring appears in the larger string.
	 * </p>
	 *
	 * <p>
	 * A {@code null} or empty ("") String input returns {@code 0}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", null)  = 0
	 * StringUtils.countMatches("abba", "")    = 0
	 * StringUtils.countMatches("abba", "a")   = 2
	 * StringUtils.countMatches("abba", "ab")  = 1
	 * StringUtils.countMatches("abba", "xxx") = 0
	 * </pre>
	 *
	 * @param str the CharSequence to check, may be null
	 * @param sub the substring to count, may be null
	 * @return the number of occurrences, 0 if either CharSequence is {@code null}
	 * @since 3.0 Changed signature from countMatches(String, String) to
	 *        countMatches(CharSequence, CharSequence)
	 */
	public static int countMatches(final CharSequence str, final CharSequence sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	/**
	 * <p>
	 * Counts how many times the char appears in the given string.
	 * </p>
	 *
	 * <p>
	 * A {@code null} or empty ("") String input returns {@code 0}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", 0)  = 0
	 * StringUtils.countMatches("abba", 'a')   = 2
	 * StringUtils.countMatches("abba", 'b')  = 2
	 * StringUtils.countMatches("abba", 'x') = 0
	 * </pre>
	 *
	 * @param str the CharSequence to check, may be null
	 * @param ch  the char to count
	 * @return the number of occurrences, 0 if the CharSequence is {@code null}
	 * @since 3.4
	 */
	public static int countMatches(final CharSequence str, final char ch) {
		if (isEmpty(str)) {
			return 0;
		}
		int count = 0;
		// We could also call str.toCharArray() for faster look ups but that would
		// generate more garbage.
		for (int i = 0; i < str.length(); i++) {
			if (ch == str.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	// Character Tests
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters.
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence (length()=0)
	 * will return {@code false}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlpha(null)   = false
	 * StringUtils.isAlpha("")     = false
	 * StringUtils.isAlpha("  ")   = false
	 * StringUtils.isAlpha("abc")  = true
	 * StringUtils.isAlpha("ab2c") = false
	 * StringUtils.isAlpha("ab-c") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains letters, and is non-null
	 * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
	 * @since 3.0 Changed "" to return false and not true
	 */
	public static boolean isAlpha(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters and space (' ').
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false} An empty CharSequence (length()=0)
	 * will return {@code true}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphaSpace(null)   = false
	 * StringUtils.isAlphaSpace("")     = true
	 * StringUtils.isAlphaSpace("  ")   = true
	 * StringUtils.isAlphaSpace("abc")  = true
	 * StringUtils.isAlphaSpace("ab c") = true
	 * StringUtils.isAlphaSpace("ab2c") = false
	 * StringUtils.isAlphaSpace("ab-c") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains letters and space, and is non-null
	 * @since 3.0 Changed signature from isAlphaSpace(String) to
	 *        isAlphaSpace(CharSequence)
	 */
	public static boolean isAlphaSpace(final CharSequence cs) {
		if (cs == null) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters or digits.
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence (length()=0)
	 * will return {@code false}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphanumeric(null)   = false
	 * StringUtils.isAlphanumeric("")     = false
	 * StringUtils.isAlphanumeric("  ")   = false
	 * StringUtils.isAlphanumeric("abc")  = true
	 * StringUtils.isAlphanumeric("ab c") = false
	 * StringUtils.isAlphanumeric("ab2c") = true
	 * StringUtils.isAlphanumeric("ab-c") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains letters or digits, and is non-null
	 * @since 3.0 Changed signature from isAlphanumeric(String) to
	 *        isAlphanumeric(CharSequence)
	 * @since 3.0 Changed "" to return false and not true
	 */
	public static boolean isAlphanumeric(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetterOrDigit(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters, digits or space
	 * ({@code ' '}).
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence (length()=0)
	 * will return {@code true}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphanumericSpace(null)   = false
	 * StringUtils.isAlphanumericSpace("")     = true
	 * StringUtils.isAlphanumericSpace("  ")   = true
	 * StringUtils.isAlphanumericSpace("abc")  = true
	 * StringUtils.isAlphanumericSpace("ab c") = true
	 * StringUtils.isAlphanumericSpace("ab2c") = true
	 * StringUtils.isAlphanumericSpace("ab-c") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains letters, digits or space, and is
	 *         non-null
	 * @since 3.0 Changed signature from isAlphanumericSpace(String) to
	 *        isAlphanumericSpace(CharSequence)
	 */
	public static boolean isAlphanumericSpace(final CharSequence cs) {
		if (cs == null) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode digits. A decimal point is
	 * not a Unicode digit and returns false.
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence (length()=0)
	 * will return {@code false}.
	 * </p>
	 *
	 * <p>
	 * Note that the method does not allow for a leading sign, either positive or
	 * negative. Also, if a String passes the numeric test, it may still generate a
	 * NumberFormatException when parsed by Integer.parseInt or Long.parseLong, e.g.
	 * if the value is outside the range for int or long respectively.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = false
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * StringUtils.isNumeric("-123") = false
	 * StringUtils.isNumeric("+123") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains digits, and is non-null
	 * @since 3.0 Changed signature from isNumeric(String) to
	 *        isNumeric(CharSequence)
	 * @since 3.0 Changed "" to return false and not true
	 */
	public static boolean isNumeric(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode digits or space
	 * ({@code ' '}). A decimal point is not a Unicode digit and returns false.
	 * </p>
	 *
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence (length()=0)
	 * will return {@code true}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNumericSpace(null)   = false
	 * StringUtils.isNumericSpace("")     = true
	 * StringUtils.isNumericSpace("  ")   = true
	 * StringUtils.isNumericSpace("123")  = true
	 * StringUtils.isNumericSpace("12 3") = true
	 * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
	 * StringUtils.isNumeric("\u0967\u0968 \u0969")  = true
	 * StringUtils.isNumericSpace("ab2c") = false
	 * StringUtils.isNumericSpace("12-3") = false
	 * StringUtils.isNumericSpace("12.3") = false
	 * </pre>
	 *
	 * @param cs the CharSequence to check, may be null
	 * @return {@code true} if only contains digits or space, and is non-null
	 * @since 3.0 Changed signature from isNumericSpace(String) to
	 *        isNumericSpace(CharSequence)
	 */
	public static boolean isNumericSpace(final CharSequence cs) {
		if (cs == null) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}

	public static String join(final Iterator<?> iterator, final char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
			return Objects.toString(first, EMPTY);
		}

		// two or more elements
		final StringBuilder buf = new StringBuilder(STRING_BUILDER_SIZE); // Java default is 16, probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator is
	 * the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterator  the {@code Iterator} of values to join together, may be null
	 * @param separator the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(final Iterator<?> iterator, final String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		final Object first = iterator.next();
		if (!iterator.hasNext()) {
			return Objects.toString(first, "");
		}

		// two or more elements
		final StringBuilder buf = new StringBuilder(STRING_BUILDER_SIZE); // Java default is 16, probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			final Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterable  the {@code Iterable} providing the values to join together,
	 *                  may be null
	 * @param separator the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(final Iterable<?> iterable, final char separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator is
	 * the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterable  the {@code Iterable} providing the values to join together,
	 *                  may be null
	 * @param separator the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(final Iterable<?> iterable, final String separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code List} into a single String
	 * containing the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param list       the {@code List} of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the list
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the list
	 * @return the joined String, {@code null} if null list input
	 * @since 3.8
	 */
	public static String join(final List<?> list, final char separator, final int startIndex, final int endIndex) {
		if (list == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final List<?> subList = list.subList(startIndex, endIndex);
		return join(subList.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code List} into a single String
	 * containing the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param list       the {@code List} of values to join together, may be null
	 * @param separator  the separator character to use
	 * @param startIndex the first index to start joining from. It is an error to
	 *                   pass in a start index past the end of the list
	 * @param endIndex   the index to stop joining from (exclusive). It is an error
	 *                   to pass in an end index past the end of the list
	 * @return the joined String, {@code null} if null list input
	 * @since 3.8
	 */
	public static String join(final List<?> list, final String separator, final int startIndex, final int endIndex) {
		if (list == null) {
			return null;
		}
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return EMPTY;
		}
		final List<?> subList = list.subList(startIndex, endIndex);
		return join(subList.iterator(), separator);
	}

	/**
	 * <p>
	 * Deletes all whitespaces from a String as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 *
	 * @param str the String to delete whitespace from, may be null
	 * @return the String without whitespaces, {@code null} if null String input
	 */
	public static String deleteWhitespace(final String str) {
		if (isEmpty(str)) {
			return str;
		}
		final int sz = str.length();
		final char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
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

//	public static String toString(byte[] uncompress) {
//		return new String(uncompress, CommonUtils.UTF8_CHARSET);
//	}

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
	public static final String EMPTY = "";

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

	public static <T> boolean isNullOrBlank(T value) {
		if (null == value) {
			return true;
		}
		return String.valueOf(value).length() != 0;
	}

	public static <T> boolean isNotBlank(T value) {
		return !isNullOrBlank(value);
	}

	// Defaults
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Returns either the passed in String, or if the String is {@code null}, an
	 * empty String ("").
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultString(null)  = ""
	 * StringUtils.defaultString("")    = ""
	 * StringUtils.defaultString("bat") = "bat"
	 * </pre>
	 *
	 * @see ObjectUtils#toString(Object)
	 * @see String#valueOf(Object)
	 * @param str the String to check, may be null
	 * @return the passed in String, or the empty String if it was {@code null}
	 */
	public static String defaultString(final String str) {
		return defaultString(str, EMPTY);
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is {@code null}, the
	 * value of {@code defaultStr}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultString(null, "NULL")  = "NULL"
	 * StringUtils.defaultString("", "NULL")    = ""
	 * StringUtils.defaultString("bat", "NULL") = "bat"
	 * </pre>
	 *
	 * @see ObjectUtils#toString(Object,String)
	 * @see String#valueOf(Object)
	 * @param str        the String to check, may be null
	 * @param defaultStr the default String to return if the input is {@code null},
	 *                   may be null
	 * @return the passed in String, or the default if it was {@code null}
	 */
	public static String defaultString(final String str, final String defaultStr) {
		return str == null ? defaultStr : str;
	}

	public static <T> boolean isEmpty(T value) {
		return isNullOrBlank(value);
	}

	static final boolean accept(final Field field) {
//	        if (field.getName().indexOf(ClassUtils.INNER_CLASS_SEPARATOR_CHAR) != -1) {
//	            // Reject field from inner class.
//	            return false;
//	        }
//	        if (Modifier.isTransient(field.getModifiers()) && !this.isAppendTransients()) {
//	            // Reject transient fields.
//	            return false;
//	        }
//	        if (Modifier.isStatic(field.getModifiers()) && !this.isAppendStatics()) {
//	            // Reject static fields.
//	            return false;
//	        }
//	        if (this.excludeFieldNames != null
//	            && Arrays.binarySearch(this.excludeFieldNames, field.getName()) >= 0) {
//	            // Reject fields from the getExcludeFieldNames list.
//	            return false;
//	        }
//	        return !field.isAnnotationPresent(ToStringExclude.class);
		return true;
	}

	static final void appendFieldsIn(final Object t, StringBuilder sb) {
		Class<?> clazz = t.getClass();
		if (clazz.isArray()) {
//            this.reflectionAppendArray(t);
			return;
		}
		final Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		boolean first = true;
		sb.append("{");
		for (final Field field : fields) {
			final String fieldName = field.getName();
			if (accept(field)) {
				if (!first) {
					sb.append(",\n  ");
				}
				try {
					final Object fieldValue = field.get(t);
					if(null==fieldValue||fieldValue.getClass().isPrimitive() 
							|| fieldValue instanceof CharSequence || fieldValue instanceof Collection
							|| fieldValue instanceof Throwable|| fieldValue instanceof Number) {
						sb.append(fieldName).append("=").append(fieldValue);// ,	
					}else {
						sb.append(fieldName).append(":");
						appendFieldsIn(fieldValue,sb);// ,	
					}
				} catch (final IllegalAccessException ex) {
					// this can't happen. Would get a Security exception
					// instead
					// throw a runtime exception in case the impossible
					// happens.
					throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
				}
				first = false;
			}
		}
		sb.append("}");
	}

	/**
	 * <p>
	 * Gets the String built by this builder.
	 * </p>
	 *
	 * @return the built string
	 */
	public static final String toString(Object t) {
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = t.getClass();
		appendFieldsIn(t, sb);
		return sb.toString();
	}

}
