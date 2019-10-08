package org.basic.common.util;

public class ArrayUtils {
	/**
	 * An empty immutable {@code Object} array.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	/**
	 * An empty immutable {@code Class} array.
	 */
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
	/**
	 * An empty immutable {@code String} array.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	/**
	 * An empty immutable {@code long} array.
	 */
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	/**
	 * An empty immutable {@code Long} array.
	 */
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	/**
	 * An empty immutable {@code int} array.
	 */
	public static final int[] EMPTY_INT_ARRAY = new int[0];
	/**
	 * An empty immutable {@code Integer} array.
	 */
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	/**
	 * An empty immutable {@code short} array.
	 */
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	/**
	 * An empty immutable {@code Short} array.
	 */
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	/**
	 * An empty immutable {@code byte} array.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	/**
	 * An empty immutable {@code Byte} array.
	 */
	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	/**
	 * An empty immutable {@code double} array.
	 */
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	/**
	 * An empty immutable {@code Double} array.
	 */
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	/**
	 * An empty immutable {@code float} array.
	 */
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	/**
	 * An empty immutable {@code Float} array.
	 */
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	/**
	 * An empty immutable {@code boolean} array.
	 */
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	/**
	 * An empty immutable {@code Boolean} array.
	 */
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	/**
	 * An empty immutable {@code char} array.
	 */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	/**
	 * An empty immutable {@code Character} array.
	 */
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

	/**
	 * The index value when an element is not found in a list or array: {@code -1}.
	 * This value is returned by methods in this class and can also be used in
	 * comparisons with values returned by various method from
	 * {@link java.util.List}.
	 */
	public static final int INDEX_NOT_FOUND = -1;

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Shallow clones an array returning a typecast result and handling
	 * {@code null}.
	 *
	 * <p>
	 * The objects in the array are not cloned, thus there is no special handling
	 * for multi-dimensional arrays.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param       <T> the component type of the array
	 * @param array the array to shallow clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static <T> T[] clone(final T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Checks whether two arrays are the same length, treating {@code null} arrays
	 * as length {@code 0}.
	 *
	 * <p>
	 * Any multi-dimensional aspects of the arrays are ignored.
	 *
	 * @param array1 the first array, may be {@code null}
	 * @param array2 the second array, may be {@code null}
	 * @return {@code true} if length of arrays matches, treating {@code null} as an
	 *         empty array
	 */
	public static <T> boolean isSameLength(final T[] array1, final T[] array2) {
		return array1.length == array1.length;
	}

	public static String toString(Class<?>[] parameterTypes) {
		StringBuilder output = new StringBuilder("");
		for (Class<?> c : parameterTypes) {

		}
		return output.toString();
	}

}