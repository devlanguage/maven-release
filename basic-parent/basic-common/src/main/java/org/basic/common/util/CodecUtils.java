package org.basic.common.util;

import java.io.ByteArrayInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.jar.JarInputStream;

/**
 * Utility methods for manipulating base64 converter
 * 
 * <pre>
 * Encoding   Alphabet            Ratio  Comments
 * ----------------------------------------------------
 * Base64URL:  A-Z a-z 0-9 - _    1.33   Safe to use as filenames, or to pass in URLs without escaping
 * Base64:     A-Z a-z 0-9 + /    1.33
 * Base32:     A-Z 2-7            1.60   Human-readable; no possibility of mixing up 0/O or 1/I.  Defaults to upper case.
 * Base32Hex:  0-9 A-V            1.60   "Numerical" base 32; extended from the traditional hex alphabet.  Defaults to upper case
 * Base16:     0-9 A-F            2.00   Traditional hexadecimal.  Defaults to upper case
 * </pre>
 *
 */
public class CodecUtils {

    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder().withoutPadding();
    private static final Base64.Encoder BASE64_ENCODER_WITHPADDING = Base64.getEncoder();

    private static final Base64.Decoder BASE64_URLDECODER = Base64.getUrlDecoder();
    private static final Base64.Encoder BASE64_URLENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Encoder BASE64_URLENCODER_WITHPADING = Base64.getUrlEncoder();
    private static char[] BASE16_CHARACTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };
    private static char[] BASE16_CHARACTERS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F' };
    private static final String BASE32_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int[] BASE32_LOOKUP = { 0xFF, 0xFF, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, // '0', '1', '2', '3',
                                                                                                 // '4', '5', '6', '7'
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, // '8', '9', ':', ';', '<', '=', '>', '?'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, // '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, // 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, // 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, // 'X', 'Y', 'Z', '[', '\', ']', '^', '_'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, // '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, // 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, // 'p', 'q', 'r', 's', 't', 'u', 'v', 'w'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF // 'x', 'y', 'z', '{', '|', '}', '~', 'DEL'
    };

    /**
     * Encodes one byte array as Hexadecimal string
     * 
     * @param bytes original byte array
     * @return Hexadecimal string
     */
    public static final String encodeHex(byte[] bytes) {
        return new String(encodeHex(bytes, BASE16_CHARACTERS));
    }

    public static final String encodeBase16(byte[] bytes) {
        return new String(encodeHex(bytes, BASE16_CHARACTERS));
    }

    /**
     * Encodes one byte array as lower or uppercase Hexadecimal string
     * 
     * @param bytes       original byte array
     * @param toLowerCase indicate if hexadecimal string should use the upper case
     * @return Hexadecimal string
     */
    public static final String encodeHex(byte[] bytes, boolean toLowerCase) {

        return new String(encodeHex(bytes, toLowerCase ? BASE16_CHARACTERS : BASE16_CHARACTERS_UPPER));
    }

    public static final String encodeBase16(byte[] bytes, boolean toLowerCase) {
        return new String(encodeHex(bytes, toLowerCase ? BASE16_CHARACTERS : BASE16_CHARACTERS_UPPER));

    }

    private static final char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    public static final byte[] decodeBase16(String src) {
        return decodeHex(src);
    }

    public static final byte[] decodeHex(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] output = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            output[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
        }
        return output;

    }

    //////////

    public static String encodeBase32(final byte[] bytes) {
        int i = 0, index = 0, digit = 0;
        int currByte, nextByte;
        StringBuffer base32 = new StringBuffer((bytes.length + 7) * 8 / 5);

        while (i < bytes.length) {
            currByte = (bytes[i] >= 0) ? bytes[i] : (bytes[i] + 256); // unsign

            /* Is the current digit going to span a byte boundary? */
            if (index > 3) {
                if ((i + 1) < bytes.length) {
                    nextByte = (bytes[i + 1] >= 0) ? bytes[i + 1] : (bytes[i + 1] + 256);
                } else {
                    nextByte = 0;
                }

                digit = currByte & (0xFF >> index);
                index = (index + 5) % 8;
                digit <<= index;
                digit |= nextByte >> (8 - index);
                i++;
            } else {
                digit = (currByte >> (8 - (index + 5))) & 0x1F;
                index = (index + 5) % 8;
                if (index == 0) {
                    i++;
                }
            }
            base32.append(BASE32_CHARACTERS.charAt(digit));
        }

        return base32.toString();
    }

    public static byte[] decodeBase32(final String base32) {
        int i, index, lookup, offset, digit;
        byte[] bytes = new byte[base32.length() * 5 / 8];

        for (i = 0, index = 0, offset = 0; i < base32.length(); i++) {
            lookup = base32.charAt(i) - '0';

            /* Skip chars outside the lookup table */
            if (lookup < 0 || lookup >= BASE32_LOOKUP.length) {
                continue;
            }

            digit = BASE32_LOOKUP[lookup];

            /* If this digit is not in the table, ignore it */
            if (digit == 0xFF) {
                continue;
            }

            if (index <= 3) {
                index = (index + 5) % 8;
                if (index == 0) {
                    bytes[offset] |= digit;
                    offset++;
                    if (offset >= bytes.length) {
                        break;
                    }
                } else {
                    bytes[offset] |= digit << (8 - index);
                }
            } else {
                index = (index + 5) % 8;
                bytes[offset] |= (digit >>> index);
                offset++;

                if (offset >= bytes.length) {
                    break;
                }
                bytes[offset] |= digit << (8 - index);
            }
        }
        return bytes;
    }

    /**
     * Encodes one byte array as string in base64
     * 
     * @param src source byte array
     * @return encoded string
     */
    public static final String encodeBase64String(byte[] src) {
        return BASE64_ENCODER.encodeToString(src);
    }

    /**
     * Encodes one byte array as string in base64
     * 
     * @param src     source byte array
     * @param padding if adding one padding character at the end
     * @return encoded string
     */
    public static final String encodeBase64String(byte[] src, boolean padding) {
        return padding ? BASE64_ENCODER_WITHPADDING.encodeToString(src) : BASE64_ENCODER.encodeToString(src);
    }

    /**
     * Encodes one string as byte array in base64
     * 
     * @param src source string
     * @return encoded byte array
     */
    public static final byte[] encodeBase64(String src) {
        return BASE64_ENCODER.encode(src.getBytes());
    }

    /**
     * Encodes one string as byte array in base64
     * 
     * @param src     source string
     * @param padding if adding one padding character at the end
     * @return encoded byte array
     */
    public static final byte[] encodeBase64(String src, boolean padding) {
        return padding ? BASE64_ENCODER_WITHPADDING.encode(src.getBytes()) : BASE64_ENCODER.encode(src.getBytes());
    }

    /**
     * Encodes one byte array as byte array in base64
     * 
     * @param src source byte array
     * @return encoded string
     */
    public static final byte[] encodeBase64(byte[] src) {
        return BASE64_ENCODER.encode(src);
    }

    /**
     * Encodes one byte array as byte array in base64
     * 
     * @param src     source byte array
     * @param padding if adding one padding character at the end
     * @return encoded string
     */
    public static final byte[] encodeBase64(byte[] src, boolean padding) {
        return padding ? BASE64_ENCODER_WITHPADDING.encode(src) : BASE64_ENCODER.encode(src);
    }

    /**
     * Encodes one byte array as {@link ByteBuffer} object in base64
     * 
     * @param src source byte array
     * @return encoded string
     */
    public static final ByteBuffer encodeBase64(ByteBuffer src) {
        return BASE64_ENCODER.encode(src);
    }

    /**
     * Encodes one byte array as {@link ByteBuffer} object in base64
     * 
     * @param src     source byte array
     * @param padding if adding one padding character at the end
     * @return encoded string
     */
    public static final ByteBuffer encodeBase64(ByteBuffer src, boolean padding) {
        return padding ? BASE64_ENCODER_WITHPADDING.encode(src) : BASE64_ENCODER.encode(src);
    }

    /**
     * Decodes one string in base64 to byte array
     * 
     * @param src one string encoded in base64
     * @return encoded string
     */
    public static final byte[] decodeBase64(String src) {
        return BASE64_DECODER.decode(src);
    }

    /**
     * Decodes one byte array in base64 to byte array
     * 
     * @param src one string encoded in base64
     * @return encoded string
     */
    public static final byte[] decodeBase64(byte[] src) {
        return BASE64_DECODER.decode(src);
    }

    /**
     * Decodes one {@link ByteBuffer} in base64 to {@link ByteBuffer}
     * 
     * @param src one {@link ByteBuffer} Object encoded in base64
     * @return encoded string
     */

    public static final ByteBuffer decodeBase64(ByteBuffer src) {
        return BASE64_DECODER.decode(src);
    }

    ///////// URL Decodeing/Encoding//////////////
    /**
     * Encodes one byte array to string using the <a href="#url">URL and Filename
     * safe</a> type base64 encoding scheme.
     * 
     * @param src source byte array
     * @return encoded string in base64
     */
    public static final String encodeBase64URLSafeString(byte[] src) {
        return BASE64_URLENCODER.encodeToString(src);
    }

    /**
     * Encodes one byte array to string using the <a href="#url">URL and Filename
     * safe</a> type base64 encoding scheme.
     * 
     * @param src     source byte array
     * @param padding if adding one padding character at the end
     * @return encoded string in base64
     */
    public static final String encodeBase64URLSafeString(byte[] src, boolean padding) {
        return padding ? BASE64_URLENCODER_WITHPADING.encodeToString(src) : BASE64_URLENCODER.encodeToString(src);
    }

    /**
     * Encodes one byte array to byte array using the <a href="#url">URL and
     * Filename safe</a> type base64 encoding scheme.
     * 
     * @param src source byte array
     * @return encoded byte array in base64
     */
    public static final byte[] encodeBase64URL(byte[] src) {
        return BASE64_URLENCODER.encode(src);
    }

    /**
     * Encodes one byte array to byte array using the <a href="#url">URL and
     * Filename safe</a> type base64 encoding scheme.
     * 
     * @param src     source byte array
     * @param padding if adding one padding character at the end
     * @return encoded byte array in base64
     */
    public static final byte[] encodeBase64URL(byte[] src, boolean padding) {
        return padding ? BASE64_URLENCODER_WITHPADING.encode(src) : BASE64_URLENCODER.encode(src);
    }

    /**
     * Decodes one string encoded using the <a href="#url">URL and Filename safe</a>
     * type base64 encoding scheme
     * 
     * @param src encoded string in base64
     * @return decoded byte array
     */
    public static final byte[] decodeBase64URL(String src) {
        return BASE64_URLDECODER.decode(src);
    }

    /**
     * Decodes one byte array encoded using the <a href="#url">URL and Filename
     * safe</a> type base64 encoding scheme
     * 
     * @param src encoded byte array in base64
     * @return decoded byte array
     */
    public static final byte[] decodeBase64URL(byte[] src) {
        return BASE64_URLDECODER.decode(src);
    }

    /**
     * Checks if byte array specified by {@code jarContent} is one jar file
     * 
     * @param jarContent byte array of jar file
     * @return
     */
    public static final boolean isValidJar(byte[] jarContent) {
        try (JarInputStream jar = new JarInputStream(new ByteArrayInputStream(jarContent))) {
            return null != jar.getNextJarEntry() || null != jar.getManifest();
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {

        String urlString = "http://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdfhttp://www.test.com?t1=23&t2=sdf";
//		Encoder rfc4648 = java.util.Base64.getEncoder();
//		Encoder rfc4648UrlSafe = java.util.Base64.getUrlEncoder();
//		Encoder rfc2045 = java.util.Base64.getMimeEncoder();
//
//		System.out.println(rfc4648.encodeToString(urlString.getBytes()));
//		System.out.println(rfc4648UrlSafe.encodeToString(urlString.getBytes()));
//		System.out.println(rfc2045.encodeToString(urlString.getBytes()));

        int size = 1000000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; ++i) {
            encodeHex(urlString.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < size; ++i) {
            encodeHex(urlString.getBytes(), BASE16_CHARACTERS);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);

    }
}

class Base64 {

    private Base64() {
    }

    /**
     * Returns a {@link Encoder} that encodes using the <a href="#basic">Basic</a>
     * type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    /**
     * Returns a {@link Encoder} that encodes using the <a href="#url">URL and
     * Filename safe</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    static Encoder getUrlEncoder() {
        return Encoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Encoder} that encodes using the <a href="#mime">MIME</a>
     * type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    /**
     * Returns a {@link Decoder} that decodes using the <a href="#basic">Basic</a>
     * type base64 encoding scheme.
     *
     * @return A Base64 decoder.
     */
    static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    /**
     * Returns a {@link Decoder} that decodes using the <a href="#url">URL and
     * Filename safe</a> type base64 encoding scheme.
     *
     * @return A Base64 decoder.
     */
    static Decoder getUrlDecoder() {
        return Decoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Decoder} that decodes using the <a href="#mime">MIME</a>
     * type base64 decoding scheme.
     *
     * @return A Base64 decoder.
     */
    static Decoder getMimeDecoder() {
        return Decoder.RFC2045;
    }

    /**
     * This class implements an encoder for encoding byte data using the Base64
     * encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p>
     * Instances of {@link Encoder} class are safe for use by multiple concurrent
     * threads.
     *
     * <p>
     * Unless otherwise noted, passing a {@code null} argument to a method of this
     * class will cause a {@link java.lang.NullPointerException
     * NullPointerException} to be thrown.
     *
     */
    static class Encoder {

        private final byte[] newline;
        private final int linemax;
        private final boolean isURL;
        private final boolean doPadding;

        private Encoder(boolean isURL, byte[] newline, int linemax, boolean doPadding) {
            this.isURL = isURL;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        /**
         * This array is a lookup table that translates 6-bit positive integer index
         * values into their "Base64 Alphabet" equivalents as specified in "Table 1: The
         * Base64 Alphabet" of RFC 2045 (and RFC 4648).
         */
        private static final char[] toBase64 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                '4', '5', '6', '7', '8', '9', '+', '/' };

        /**
         * It's the lookup table for "URL and Filename safe Base64" as specified in
         * Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and '_'. This
         * table is used when BASE64_URL is specified.
         */
        private static final char[] toBase64URL = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
                '3', '4', '5', '6', '7', '8', '9', '-', '_' };

        private static final int MIMELINEMAX = 76;
        private static final byte[] CRLF = new byte[] { '\r', '\n' };

        static final Encoder RFC4648 = new Encoder(false, null, -1, true);
        static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);
        static final Encoder RFC2045 = new Encoder(false, CRLF, MIMELINEMAX, true);

        private final int outLength(int srclen) {
            int len = 0;
            if (doPadding) {
                len = 4 * ((srclen + 2) / 3);
            } else {
                int n = srclen % 3;
                len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
            }
            if (linemax > 0) // line separators
                len += (len - 1) / linemax * newline.length;
            return len;
        }

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated byte
         * array using the {@link Base64} encoding scheme. The returned byte array is of
         * the length of the resulting bytes.
         *
         * @param src the byte array to encode
         * @return A newly-allocated byte array containing the resulting encoded bytes.
         */
        byte[] encode(byte[] src) {
            int len = outLength(src.length); // dst array size
            byte[] dst = new byte[len];
            int ret = encode0(src, 0, src.length, dst);
            if (ret != dst.length)
                return Arrays.copyOf(dst, ret);
            return dst;
        }

        /**
         * Encodes all bytes from the specified byte array using the {@link Base64}
         * encoding scheme, writing the resulting bytes to the given output byte array,
         * starting at offset 0.
         *
         * <p>
         * It is the responsibility of the invoker of this method to make sure the
         * output byte array {@code dst} has enough space for encoding all bytes from
         * the input byte array. No bytes will be written to the output byte array if
         * the output byte array is not big enough.
         *
         * @param src the byte array to encode
         * @param dst the output byte array
         * @return The number of bytes written to the output byte array
         *
         * @throws IllegalArgumentException if {@code dst} does not have enough space
         *                                  for encoding all input bytes.
         */
        int encode(byte[] src, byte[] dst) {
            int len = outLength(src.length); // dst array size
            if (dst.length < len)
                throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
            return encode0(src, 0, src.length, dst);
        }

        /**
         * Encodes the specified byte array into a String using the {@link Base64}
         * encoding scheme.
         *
         * <p>
         * This method first encodes all input bytes into a base64 encoded byte array
         * and then constructs a new String by using the encoded byte array and the
         * {@link java.nio.charset.StandardCharsets#ISO_8859_1 ISO-8859-1} charset.
         *
         * <p>
         * In other words, an invocation of this method has exactly the same effect as
         * invoking {@code new String(encode(src), StandardCharsets.ISO_8859_1)}.
         *
         * @param src the byte array to encode
         * @return A String containing the resulting Base64 encoded characters
         */
        @SuppressWarnings("deprecation")
        String encodeToString(byte[] src) {
            byte[] encoded = encode(src);
            return new String(encoded, 0, 0, encoded.length);
        }

        /**
         * Encodes all remaining bytes from the specified byte buffer into a
         * newly-allocated ByteBuffer using the {@link Base64} encoding scheme.
         *
         * Upon return, the source buffer's position will be updated to its limit; its
         * limit will not have been changed. The returned output buffer's position will
         * be zero and its limit will be the number of resulting encoded bytes.
         *
         * @param buffer the source ByteBuffer to encode
         * @return A newly-allocated byte buffer containing the encoded bytes.
         */
        ByteBuffer encode(ByteBuffer buffer) {
            int len = outLength(buffer.remaining());
            byte[] dst = new byte[len];
            int ret = 0;
            if (buffer.hasArray()) {
                ret = encode0(buffer.array(), buffer.arrayOffset() + buffer.position(),
                        buffer.arrayOffset() + buffer.limit(), dst);
                buffer.position(buffer.limit());
            } else {
                byte[] src = new byte[buffer.remaining()];
                buffer.get(src);
                ret = encode0(src, 0, src.length, dst);
            }
            if (ret != dst.length)
                dst = Arrays.copyOf(dst, ret);
            return ByteBuffer.wrap(dst);
        }

        /**
         * Wraps an output stream for encoding byte data using the {@link Base64}
         * encoding scheme.
         *
         * <p>
         * It is recommended to promptly close the returned output stream after use,
         * during which it will flush all possible leftover bytes to the underlying
         * output stream. Closing the returned output stream will close the underlying
         * output stream.
         *
         * @param os the output stream.
         * @return the output stream for encoding the byte data into the specified
         *         Base64 encoded format
         */
        OutputStream wrap(OutputStream os) {
            return new EncOutputStream(os, isURL ? toBase64URL : toBase64, newline, linemax, doPadding);
        }

        /**
         * Returns an encoder instance that encodes equivalently to this one, but
         * without adding any padding character at the end of the encoded byte data.
         *
         * <p>
         * The encoding scheme of this encoder instance is unaffected by this
         * invocation. The returned encoder instance should be used for non-padding
         * encoding operation.
         *
         * @return an equivalent encoder that encodes without adding any padding
         *         character at the end
         */
        Encoder withoutPadding() {
            if (!doPadding)
                return this;
            return new Encoder(isURL, newline, linemax, false);
        }

        private int encode0(byte[] src, int off, int end, byte[] dst) {
            char[] base64 = isURL ? toBase64URL : toBase64;
            int sp = off;
            int slen = (end - off) / 3 * 3;
            int sl = off + slen;
            if (linemax > 0 && slen > linemax / 4 * 3)
                slen = linemax / 4 * 3;
            int dp = 0;
            while (sp < sl) {
                int sl0 = Math.min(sp + slen, sl);
                for (int sp0 = sp, dp0 = dp; sp0 < sl0;) {
                    int bits = (src[sp0++] & 0xff) << 16 | (src[sp0++] & 0xff) << 8 | (src[sp0++] & 0xff);
                    dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                    dst[dp0++] = (byte) base64[bits & 0x3f];
                }
                int dlen = (sl0 - sp) / 3 * 4;
                dp += dlen;
                sp = sl0;
                if (dlen == linemax && sp < end) {
                    for (byte b : newline) {
                        dst[dp++] = b;
                    }
                }
            }
            if (sp < end) { // 1 or 2 leftover bytes
                int b0 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[b0 >> 2];
                if (sp == end) {
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                        dst[dp++] = '=';
                    }
                } else {
                    int b1 = src[sp++] & 0xff;
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                    dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                    }
                }
            }
            return dp;
        }
    }

    /**
     * This class implements a decoder for decoding byte data using the Base64
     * encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p>
     * The Base64 padding character {@code '='} is accepted and interpreted as the
     * end of the encoded byte data, but is not required. So if the final unit of
     * the encoded byte data only has two or three Base64 characters (without the
     * corresponding padding character(s) padded), they are decoded as if followed
     * by padding character(s). If there is a padding character present in the final
     * unit, the correct number of padding character(s) must be present, otherwise
     * {@code IllegalArgumentException} ( {@code IOException} when reading from a
     * Base64 stream) is thrown during decoding.
     *
     * <p>
     * Instances of {@link Decoder} class are safe for use by multiple concurrent
     * threads.
     *
     * <p>
     * Unless otherwise noted, passing a {@code null} argument to a method of this
     * class will cause a {@link java.lang.NullPointerException
     * NullPointerException} to be thrown.
     *
     */
    static class Decoder {

        private final boolean isURL;
        private final boolean isMIME;

        private Decoder(boolean isURL, boolean isMIME) {
            this.isURL = isURL;
            this.isMIME = isMIME;
        }

        /**
         * Lookup table for decoding unicode characters drawn from the "Base64 Alphabet"
         * (as specified in Table 1 of RFC 2045) into their 6-bit positive integer
         * equivalents. Characters that are not in the Base64 alphabet but fall within
         * the bounds of the array are encoded to -1.
         *
         */
        private static final int[] fromBase64 = new int[256];

        static {
            Arrays.fill(fromBase64, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++)
                fromBase64[Encoder.toBase64[i]] = i;
            fromBase64['='] = -2;
        }

        /**
         * Lookup table for decoding "URL and Filename safe Base64 Alphabet" as
         * specified in Table2 of the RFC 4648.
         */
        private static final int[] fromBase64URL = new int[256];

        static {
            Arrays.fill(fromBase64URL, -1);
            for (int i = 0; i < Encoder.toBase64URL.length; i++)
                fromBase64URL[Encoder.toBase64URL[i]] = i;
            fromBase64URL['='] = -2;
        }

        static final Decoder RFC4648 = new Decoder(false, false);
        static final Decoder RFC4648_URLSAFE = new Decoder(true, false);
        static final Decoder RFC2045 = new Decoder(false, true);
        private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

        /**
         * Decodes all bytes from the input byte array using the {@link Base64} encoding
         * scheme, writing the results into a newly-allocated output byte array. The
         * returned byte array is of the length of the resulting bytes.
         *
         * @param src the byte array to decode
         *
         * @return A newly-allocated byte array containing the decoded bytes.
         *
         * @throws IllegalArgumentException if {@code src} is not in valid Base64 scheme
         */
        byte[] decode(byte[] src) {
            byte[] dst = new byte[outLength(src, 0, src.length)];
            int ret = decode0(src, 0, src.length, dst);
            if (ret != dst.length) {
                dst = Arrays.copyOf(dst, ret);
            }
            return dst;
        }

        /**
         * Decodes a Base64 encoded String into a newly-allocated byte array using the
         * {@link Base64} encoding scheme.
         *
         * <p>
         * An invocation of this method has exactly the same effect as invoking
         * {@code decode(src.getBytes(StandardCharsets.ISO_8859_1))}
         *
         * @param src the string to decode
         *
         * @return A newly-allocated byte array containing the decoded bytes.
         *
         * @throws IllegalArgumentException if {@code src} is not in valid Base64 scheme
         */
        byte[] decode(String src) {
            return decode(src.getBytes(UTF8_CHARSET));
        }

        /**
         * Decodes all bytes from the input byte array using the {@link Base64} encoding
         * scheme, writing the results into the given output byte array, starting at
         * offset 0.
         *
         * <p>
         * It is the responsibility of the invoker of this method to make sure the
         * output byte array {@code dst} has enough space for decoding all bytes from
         * the input byte array. No bytes will be be written to the output byte array if
         * the output byte array is not big enough.
         *
         * <p>
         * If the input byte array is not in valid Base64 encoding scheme then some
         * bytes may have been written to the output byte array before
         * IllegalargumentException is thrown.
         *
         * @param src the byte array to decode
         * @param dst the output byte array
         *
         * @return The number of bytes written to the output byte array
         *
         * @throws IllegalArgumentException if {@code src} is not in valid Base64
         *                                  scheme, or {@code dst} does not have enough
         *                                  space for decoding all input bytes.
         */
        int decode(byte[] src, byte[] dst) {
            int len = outLength(src, 0, src.length);
            if (dst.length < len)
                throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
            return decode0(src, 0, src.length, dst);
        }

        /**
         * Decodes all bytes from the input byte buffer using the {@link Base64}
         * encoding scheme, writing the results into a newly-allocated ByteBuffer.
         *
         * <p>
         * Upon return, the source buffer's position will be updated to its limit; its
         * limit will not have been changed. The returned output buffer's position will
         * be zero and its limit will be the number of resulting decoded bytes
         *
         * <p>
         * {@code IllegalArgumentException} is thrown if the input buffer is not in
         * valid Base64 encoding scheme. The position of the input buffer will not be
         * advanced in this case.
         *
         * @param buffer the ByteBuffer to decode
         *
         * @return A newly-allocated byte buffer containing the decoded bytes
         *
         * @throws IllegalArgumentException if {@code src} is not in valid Base64
         *                                  scheme.
         */
        ByteBuffer decode(ByteBuffer buffer) {
            int pos0 = buffer.position();
            try {
                byte[] src;
                int sp, sl;
                if (buffer.hasArray()) {
                    src = buffer.array();
                    sp = buffer.arrayOffset() + buffer.position();
                    sl = buffer.arrayOffset() + buffer.limit();
                    buffer.position(buffer.limit());
                } else {
                    src = new byte[buffer.remaining()];
                    buffer.get(src);
                    sp = 0;
                    sl = src.length;
                }
                byte[] dst = new byte[outLength(src, sp, sl)];
                return ByteBuffer.wrap(dst, 0, decode0(src, sp, sl, dst));
            } catch (IllegalArgumentException iae) {
                buffer.position(pos0);
                throw iae;
            }
        }

        /**
         * Returns an input stream for decoding {@link Base64} encoded byte stream.
         *
         * <p>
         * The {@code read} methods of the returned {@code InputStream} will throw
         * {@code IOException} when reading bytes that cannot be decoded.
         *
         * <p>
         * Closing the returned input stream will close the underlying input stream.
         *
         * @param is the input stream
         *
         * @return the input stream for decoding the specified Base64 encoded byte
         *         stream
         */
        InputStream wrap(InputStream is) {
            return new DecInputStream(is, isURL ? fromBase64URL : fromBase64, isMIME);
        }

        private int outLength(byte[] src, int sp, int sl) {
            int[] base64 = isURL ? fromBase64URL : fromBase64;
            int paddings = 0;
            int len = sl - sp;
            if (len == 0)
                return 0;
            if (len < 2) {
                if (isMIME && base64[0] == -1)
                    return 0;
                throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (isMIME) {
                // scan all bytes to fill out all non-alphabet. a performance
                // trade-off of pre-scan or Arrays.copyOf
                int n = 0;
                while (sp < sl) {
                    int b = src[sp++] & 0xff;
                    if (b == '=') {
                        len -= (sl - sp + 1);
                        break;
                    }
                    if ((b = base64[b]) == -1)
                        n++;
                }
                len -= n;
            } else {
                if (src[sl - 1] == '=') {
                    paddings++;
                    if (src[sl - 2] == '=')
                        paddings++;
                }
            }
            if (paddings == 0 && (len & 0x3) != 0)
                paddings = 4 - (len & 0x3);
            return 3 * ((len + 3) / 4) - paddings;
        }

        private int decode0(byte[] src, int sp, int sl, byte[] dst) {
            int[] base64 = isURL ? fromBase64URL : fromBase64;
            int dp = 0;
            int bits = 0;
            int shiftto = 18; // pos of first byte of 4-byte atom
            while (sp < sl) {
                int b = src[sp++] & 0xff;
                if ((b = base64[b]) < 0) {
                    if (b == -2) { // padding byte '='
                        // = shiftto==18 unnecessary padding
                        // x= shiftto==12 a dangling single x
                        // x to be handled together with non-padding case
                        // xx= shiftto==6&&sp==sl missing last =
                        // xx=y shiftto==6 last is not =
                        if (shiftto == 6 && (sp == sl || src[sp++] != '=') || shiftto == 18) {
                            throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
                        }
                        break;
                    }
                    if (isMIME) // skip if for rfc2045
                        continue;
                    else
                        throw new IllegalArgumentException(
                                "Illegal base64 character " + Integer.toString(src[sp - 1], 16));
                }
                bits |= (b << shiftto);
                shiftto -= 6;
                if (shiftto < 0) {
                    dst[dp++] = (byte) (bits >> 16);
                    dst[dp++] = (byte) (bits >> 8);
                    dst[dp++] = (byte) (bits);
                    shiftto = 18;
                    bits = 0;
                }
            }
            // reached end of byte array or hit padding '=' characters.
            if (shiftto == 6) {
                dst[dp++] = (byte) (bits >> 16);
            } else if (shiftto == 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
            } else if (shiftto == 12) {
                // dangling single "x", incorrectly encoded.
                throw new IllegalArgumentException("Last unit does not have enough valid bits");
            }
            // anything left is invalid, if is not MIME.
            // if MIME, ignore all non-base64 character
            while (sp < sl) {
                if (isMIME && base64[src[sp++]] < 0)
                    continue;
                throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + sp);
            }
            return dp;
        }
    }

    /*
     * An output stream for encoding bytes into the Base64.
     */
    private static class EncOutputStream extends FilterOutputStream {

        private int leftover = 0;
        private int b0, b1, b2;
        private boolean closed = false;

        private final char[] base64; // byte->base64 mapping
        private final byte[] newline; // line separator, if needed
        private final int linemax;
        private final boolean doPadding;// whether or not to pad
        private int linepos = 0;

        EncOutputStream(OutputStream os, char[] base64, byte[] newline, int linemax, boolean doPadding) {
            super(os);
            this.base64 = base64;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        @Override
        public void write(int b) throws IOException {
            byte[] buf = new byte[1];
            buf[0] = (byte) (b & 0xff);
            write(buf, 0, 1);
        }

        private void checkNewline() throws IOException {
            if (linepos == linemax) {
                out.write(newline);
                linepos = 0;
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            if (off < 0 || len < 0 || off + len > b.length)
                throw new ArrayIndexOutOfBoundsException();
            if (len == 0)
                return;
            if (leftover != 0) {
                if (leftover == 1) {
                    b1 = b[off++] & 0xff;
                    len--;
                    if (len == 0) {
                        leftover++;
                        return;
                    }
                }
                b2 = b[off++] & 0xff;
                len--;
                checkNewline();
                out.write(base64[b0 >> 2]);
                out.write(base64[(b0 << 4) & 0x3f | (b1 >> 4)]);
                out.write(base64[(b1 << 2) & 0x3f | (b2 >> 6)]);
                out.write(base64[b2 & 0x3f]);
                linepos += 4;
            }
            int nBits24 = len / 3;
            leftover = len - (nBits24 * 3);
            while (nBits24-- > 0) {
                checkNewline();
                int bits = (b[off++] & 0xff) << 16 | (b[off++] & 0xff) << 8 | (b[off++] & 0xff);
                out.write(base64[(bits >>> 18) & 0x3f]);
                out.write(base64[(bits >>> 12) & 0x3f]);
                out.write(base64[(bits >>> 6) & 0x3f]);
                out.write(base64[bits & 0x3f]);
                linepos += 4;
            }
            if (leftover == 1) {
                b0 = b[off++] & 0xff;
            } else if (leftover == 2) {
                b0 = b[off++] & 0xff;
                b1 = b[off++] & 0xff;
            }
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                if (leftover == 1) {
                    checkNewline();
                    out.write(base64[b0 >> 2]);
                    out.write(base64[(b0 << 4) & 0x3f]);
                    if (doPadding) {
                        out.write('=');
                        out.write('=');
                    }
                } else if (leftover == 2) {
                    checkNewline();
                    out.write(base64[b0 >> 2]);
                    out.write(base64[(b0 << 4) & 0x3f | (b1 >> 4)]);
                    out.write(base64[(b1 << 2) & 0x3f]);
                    if (doPadding) {
                        out.write('=');
                    }
                }
                leftover = 0;
                out.close();
            }
        }
    }

    /*
     * An input stream for decoding Base64 bytes
     */
    private static class DecInputStream extends InputStream {

        private final InputStream is;
        private final boolean isMIME;
        private final int[] base64; // base64 -> byte mapping
        private int bits = 0; // 24-bit buffer for decoding
        private int nextin = 18; // next available "off" in "bits" for input;
        // -> 18, 12, 6, 0
        private int nextout = -8; // next available "off" in "bits" for output;
        // -> 8, 0, -8 (no byte for output)
        private boolean eof = false;
        private boolean closed = false;

        DecInputStream(InputStream is, int[] base64, boolean isMIME) {
            this.is = is;
            this.base64 = base64;
            this.isMIME = isMIME;
        }

        private byte[] sbBuf = new byte[1];

        @Override
        public int read() throws IOException {
            return read(sbBuf, 0, 1) == -1 ? -1 : sbBuf[0] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            if (eof && nextout < 0) // eof and no leftover
                return -1;
            if (off < 0 || len < 0 || len > b.length - off)
                throw new IndexOutOfBoundsException();
            int oldOff = off;
            if (nextout >= 0) { // leftover output byte(s) in bits buf
                do {
                    if (len == 0)
                        return off - oldOff;
                    b[off++] = (byte) (bits >> nextout);
                    len--;
                    nextout -= 8;
                } while (nextout >= 0);
                bits = 0;
            }
            while (len > 0) {
                int v = is.read();
                if (v == -1) {
                    eof = true;
                    if (nextin != 18) {
                        if (nextin == 12)
                            throw new IOException("Base64 stream has one un-decoded dangling byte.");
                        // treat ending xx/xxx without padding character legal.
                        // same logic as v == '=' below
                        b[off++] = (byte) (bits >> (16));
                        len--;
                        if (nextin == 0) { // only one padding byte
                            if (len == 0) { // no enough output space
                                bits >>= 8; // shift to lowest byte
                                nextout = 0;
                            } else {
                                b[off++] = (byte) (bits >> 8);
                            }
                        }
                    }
                    if (off == oldOff)
                        return -1;
                    else
                        return off - oldOff;
                }
                if (v == '=') { // padding byte(s)
                    // = shiftto==18 unnecessary padding
                    // x= shiftto==12 dangling x, invalid unit
                    // xx= shiftto==6 && missing last '='
                    // xx=y or last is not '='
                    if (nextin == 18 || nextin == 12 || nextin == 6 && is.read() != '=') {
                        throw new IOException("Illegal base64 ending sequence:" + nextin);
                    }
                    b[off++] = (byte) (bits >> (16));
                    len--;
                    if (nextin == 0) { // only one padding byte
                        if (len == 0) { // no enough output space
                            bits >>= 8; // shift to lowest byte
                            nextout = 0;
                        } else {
                            b[off++] = (byte) (bits >> 8);
                        }
                    }
                    eof = true;
                    break;
                }
                if ((v = base64[v]) == -1) {
                    if (isMIME) // skip if for rfc2045
                        continue;
                    else
                        throw new IOException("Illegal base64 character " + Integer.toString(v, 16));
                }
                bits |= (v << nextin);
                if (nextin == 0) {
                    nextin = 18; // clear for next
                    nextout = 16;
                    while (nextout >= 0) {
                        b[off++] = (byte) (bits >> nextout);
                        len--;
                        nextout -= 8;
                        if (len == 0 && nextout >= 0) { // don't clean "bits"
                            return off - oldOff;
                        }
                    }
                    bits = 0;
                } else {
                    nextin -= 6;
                }
            }
            return off - oldOff;
        }

        @Override
        public int available() throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            return is.available(); // TBD:
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                is.close();
            }
        }
    }
}
