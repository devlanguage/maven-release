package org.basic.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUtils {

	private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class.getName());
	/**
	 * Represents the end-of-file (or stream).
	 * 
	 */
	public static final int EOF = -1;
	/**
	 * Cache size for received character from stream
	 */
	public static final int BUFFER_SIZE = 8024;
	/**
	 * The Unix directory separator character.
	 */
	public static final char DIR_SEPARATOR_UNIX = '/';
	/**
	 * The Windows directory separator character.
	 */
	public static final char DIR_SEPARATOR_WINDOWS = '\\';
	/**
	 * The system directory separator character.
	 */
	public static final char DIR_SEPARATOR = File.separatorChar;
	/**
	 * The Unix line separator string.
	 */
	public static final String LINE_SEPARATOR_UNIX = "\n";
	/**
	 * The Windows line separator string.
	 */
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";

	private static char[] SKIP_CHAR_BUFFER;

	/**
	 * The default buffer size to use for the skip() methods.
	 */
	private static final int SKIP_BUFFER_SIZE = 2048;

	public static void byteArrayToFile(File file, byte[] contentInBytes) throws IOException {
		// if file doesn't exists, then create it
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new IOException("Can't create file " + file);
			}
		}
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(file);
			// get the content in bytes
			fop.write(contentInBytes);
			fop.flush();
		} finally {
			if (fop != null) {
				fop.close();
			}
		}
	}

	public static void byteArrayToOutputStream(byte[] source, FileOutputStream output) throws IOException {
		output.write(source);
		output.flush();
	}

	/**
	 * Release the resource one by one. if some exception happens, exception will
	 * caught and print into logger
	 * 
	 * @param closableObjects resource object
	 */
	public static final void closeQuietly(AutoCloseable... closableObjects) {
		if (null != closableObjects) {
			for (AutoCloseable closableObject : closableObjects) {
				if (null != closableObject) {
					try {
						closableObject.close();
					} catch (Exception e) {
						logger.error("Failed to close the closeble object:" + closableObject, e);
					}
				}
			}
		}
	}

	public static String getFileContent(String filePath) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		return new String(bytes, StandardCharsets.UTF_8);
	}

	/**
	 * Build the <code>InputStream</code> on the specified the file. file path is
	 * absolute path located in operating system. for example:
	 * C:\\Software\\eclipse3.2.2\\workspace\\basic\\source\\common\\src\\main\\build.properties
	 * 
	 * @param messageFile
	 * @return
	 * @throws BasicException
	 */
	public static InputStream getInputStreamFromAbosulateClassPath(String messageFile) throws BasicException {

		FileInputStream fins = null;
		try {
			fins = new FileInputStream(messageFile);
		} catch (FileNotFoundException e) {
			logger.error("Failed to close the closeble object:" + messageFile, e);
		} finally {
			closeQuietly(fins);
		}
		return fins;
	}

	/**
	 * Build the <code>InputStream</code> on the specified the file. file path is
	 * relative path located in classs path. for example:
	 * org/basic/common/config.properties
	 * 
	 * @param messageFile
	 * @return
	 * @throws BasicException
	 */
	public final static InputStream getInputStreamFromClassPath(String fileName) {

		return StreamUtils.class.getClassLoader().getResourceAsStream(fileName);
	}

	public static String pkiStreamToString(InputStream in) throws IOException {
		StringBuilder stringBuffer = new StringBuilder();
		String line = null;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			while ((line = reader.readLine()) != null) {
				if (line.length() == 0 || line.charAt(0) == '-') {
					continue;
				} else {
					stringBuffer.append(line);
				}
			}
		} finally {
			closeQuietly(reader);
		}
		return stringBuffer.toString();
	}

	/**
	 * Copies some or all chars from a large (over 2GB) <code>InputStream</code> to
	 * an <code>OutputStream</code>, optionally skipping input chars.
	 * <p>
	 * This method uses the provided buffer, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * <p>
	 *
	 * @param input       the <code>Reader</code> to read from
	 * @param output      the <code>Writer</code> to write to
	 * @param inputOffset : number of chars to skip from input before copying -ve
	 *                    values are ignored
	 * @param length      : number of chars to copy. -ve means all
	 * @param buffer      the buffer to be used for the copy
	 * @return the number of chars copied
	 * @throws NullPointerException if the input or output is null
	 * @throws IOException          if an I/O error occurs
	 * @since 2.2
	 */
	public static long readerToWriter(final Reader input, final Writer output, final long inputOffset,
			final long length, final char[] buffer) throws IOException {
		if (inputOffset > 0) {
			skipFully(input, inputOffset);
		}
		if (length == 0) {
			return 0;
		}
		int bytesToRead = buffer.length;
		if (length > 0 && length < buffer.length) {
			bytesToRead = (int) length;
		}
		int read;
		long totalRead = 0;
		while (bytesToRead > 0 && EOF != (read = input.read(buffer, 0, bytesToRead))) {
			output.write(buffer, 0, read);
			totalRead += read;
			if (length > 0) { // only adjust length if not reading to the end
				// Note the cast must work because buffer.length is an integer
				bytesToRead = (int) Math.min(length - totalRead, buffer.length);
			}
		}
		return totalRead;
	}

	/**
	 * Skips characters from an input character stream. This implementation
	 * guarantees that it will read as many characters as possible before giving up;
	 * this may not always be the case for skip() implementations in subclasses of
	 * {@link Reader}.
	 * <p>
	 * Note that the implementation uses {@link Reader#read(char[], int, int)}
	 * rather than delegating to {@link Reader#skip(long)}. This means that the
	 * method may be considerably less efficient than using the actual skip
	 * implementation, this is done to guarantee that the correct number of
	 * characters are skipped.
	 * </p>
	 *
	 * @param input  character stream to skip
	 * @param toSkip number of characters to skip.
	 * @return number of characters actually skipped.
	 * @throws IOException              if there is a problem reading the file
	 * @throws IllegalArgumentException if toSkip is negative
	 * @see Reader#skip(long)
	 * @see <a href="https://issues.apache.org/jira/browse/IO-203">IO-203 - Add
	 *      skipFully() method for InputStreams</a>
	 * @since 2.0
	 */
	public static long skip(final Reader input, final long toSkip) throws IOException {
		if (toSkip < 0) {
			throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
		}
		/*
		 * N.B. no need to synchronize this because: - we don't care if the buffer is
		 * created multiple times (the data is ignored) - we always use the same size
		 * buffer, so if it it is recreated it will still be OK (if the buffer size were
		 * variable, we would need to synch. to ensure some other thread did not create
		 * a smaller one)
		 */
		if (SKIP_CHAR_BUFFER == null) {
			SKIP_CHAR_BUFFER = new char[SKIP_BUFFER_SIZE];
		}
		long remain = toSkip;
		while (remain > 0) {
			// See https://issues.apache.org/jira/browse/IO-203 for why we use read() rather
			// than delegating to skip()
			final long n = input.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(remain, SKIP_BUFFER_SIZE));
			if (n < 0) { // EOF
				break;
			}
			remain -= n;
		}
		return toSkip - remain;
	}

	/**
	 * Skips the requested number of characters or fail if there are not enough
	 * left.
	 * <p>
	 * This allows for the possibility that {@link Reader#skip(long)} may not skip
	 * as many characters as requested (most likely because of reaching EOF).
	 * <p>
	 * Note that the implementation uses {@link #skip(Reader, long)}. This means
	 * that the method may be considerably less efficient than using the actual skip
	 * implementation, this is done to guarantee that the correct number of
	 * characters are skipped.
	 * </p>
	 *
	 * @param input  stream to skip
	 * @param toSkip the number of characters to skip
	 * @throws IOException              if there is a problem reading the file
	 * @throws IllegalArgumentException if toSkip is negative
	 * @throws EOFException             if the number of characters skipped was
	 *                                  incorrect
	 * @see Reader#skip(long)
	 * @since 2.0
	 */
	public static void skipFully(final Reader input, final long toSkip) throws IOException {
		final long skipped = skip(input, toSkip);
		if (skipped != toSkip) {
			throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
		}
	} // Allocated in the relevant skip method if necessary.
	/*
	 * These buffers are static and are shared between threads. This is possible
	 * because the buffers are write-only - the contents are never read.
	 *
	 * N.B. there is no need to synchronize when creating these because: - we don't
	 * care if the buffer is created multiple times (the data is ignored) - we
	 * always use the same size buffer, so if it it is recreated it will still be OK
	 * (if the buffer size were variable, we would need to synch. to ensure some
	 * other thread did not create a smaller one)
	 */

	/**
	 * Read the content from {@code input} and return it as byte array
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] streamToByteArray(InputStream input) throws IOException {

		ByteArrayOutputStream output = null;
		byte[] result = null;
		try {
			output = new ByteArrayOutputStream(BUFFER_SIZE);
			streamToOutputStream(input, output);
			result = output.toByteArray();
		} finally {
			StreamUtils.closeQuietly(input, output);
		}

		return result;
	}

	/**
	 * Copies bytes from a large (over 2GB) <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method uses the provided buffer, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 *
	 * @param input  the <code>InputStream</code> to read from
	 * @param output the <code>OutputStream</code> to write to
	 * @param buffer the buffer to use for the copy
	 * @return the number of bytes copied
	 */
	public static long streamToOutputStream(final InputStream input, final OutputStream output) throws IOException {
		long count = 0;
		int bytesRead;
		byte[] buffer = new byte[BUFFER_SIZE];

		while (EOF != (bytesRead = input.read(buffer))) {
			output.write(buffer, 0, bytesRead);
			count += bytesRead;
		}
		output.flush();
		return count;
	}

	public static String streamToString(InputStream ins) throws IOException {
		return streamToString(ins, CommonUtils.UTF8);
	}

	/**
	 * Copy the contents of the given InputStream into a String. Leaves the stream
	 * open when done.
	 * 
	 * @param in the InputStream to copy from
	 * @return the String that has been copied to
	 * 
	 * @throws IOException in case of I/O errors
	 */
	public static String streamToString(InputStream in, Charset charset) throws IOException {

		StringBuilder stringBuffer = new StringBuilder();
		char[] buffer = new char[BUFFER_SIZE];
		int bytesRead = -1;

		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(in, charset);
			while ((bytesRead = reader.read(buffer)) != -1) {
				stringBuffer.append(buffer, 0, bytesRead);
			}
		} finally {
			closeQuietly(reader);
		}
		return stringBuffer.toString();
	}

	public static String streamToString(InputStream ins, String charset) throws IOException {

		int read = -1;
		byte[] cache = new byte[BUFFER_SIZE];
		StringBuilder sb = new StringBuilder();
		while ((read = ins.read(cache)) != -1) {
			sb.append(new String(cache, 0, read, charset));
		}

		return sb.toString();
	}

	public static String streamToString(Reader reader) {
		String result = null;
		try {
			StringWriter writer = new StringWriter();
			int n;
			char[] buffer = new char[BUFFER_SIZE];
			while (EOF != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
			result = writer.toString();

		} catch (IOException e) {
			throw new RuntimeException("Faid to read string from reader", e);
		} finally {
			closeQuietly(reader);
		}
		return result;
	}

	public static void stringToFile(String content, String filePath) throws IOException {
		byteArrayToFile(new File(filePath), content.getBytes(StandardCharsets.UTF_8));
	}

	public static InputStream stringToInputStream(String str) {

		if (str == null) {
			return null;
		}

		return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] stringToUtf8Bytes(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Converts the specified string to an input stream, encoded as bytes using the
	 * specified character encoding.
	 *
	 * @param input    the string to convert
	 * @param encoding the encoding to use, null means platform default
	 * @return an input stream
	 * @since 2.3
	 */
	public static InputStream toInputStream(final String input, final Charset encoding) {
		return new ByteArrayInputStream(input.getBytes(encoding));
	}

}
