/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.common.util.CodeConvertionUtil.java is created on 2007-10-11 下午04:40:08
 */
package org.basic.common.util;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import org.apache.commons.codec.DecoderException;

/**
 * Similar to org.apache.commons.codec.binary.Hex. <br>
 * Finish the convertion Between Hexidecimal and Decimal
 */
public class CodeConvertionUtil {

    /**
     * Used building output as Hex
     */
    private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Converts an array of bytes into an array of characters representing the hexidecimal values of
     * each byte in order. The returned array will be double the length of the passed array, as it
     * takes two characters to represent any given byte. <br>
     * For exmaple:
     * 
     * <pre>
     *  If Decimal=&quot;abc&quot;.getBytes();  Then Ouput: 616263
     * </pre>
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A char[] containing hexidecimal characters
     */
    public final static char[] encodeHex(byte[] decimal) {

        int count = decimal.length;
        char[] hexChars = new char[count << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < count; i++) {
            hexChars[j++] = HEX_DIGITS[(0xF0 & decimal[i]) >>> 4];
            hexChars[j++] = HEX_DIGITS[0x0F & decimal[i]];
        }
        return hexChars;
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexidecimal values of
     * each byte in order. The returned array will be double the length of the passed array, as it
     * takes two characters to represent any given byte.<br>
     * For exmaple:
     * 
     * <pre>
     *  If Decimal=&quot;616263&quot;.getBytes();  Then Ouput: abc
     * </pre>
     * 
     * @param hexidecimal
     * @return
     * @throws DecoderException
     */
    public final static byte[] decodeHex(char[] hexidecimal) throws DecoderException {

        int decodedCharCount = hexidecimal.length;

        if ((decodedCharCount & 0x01) != 0) {
            throw new DecoderException(
                    "Odd number of characters. Please make sure the number of total decoded characters is even number");
        }

        byte[] out = new byte[decodedCharCount >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < decodedCharCount; i++) {
            int f = Character.digit(hexidecimal[j++], 16) << 4;
            f = f | Character.digit(hexidecimal[j++], 16);
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /*
     * ============================================================================== Base64 utility
     * ==============================================================================
     */
    /**
     * Chunk poolSize according to RFC 2045
     */
    static final int CHUNK_SIZE = 76;

    /**
     * Chunk separator, we use a newline to separate chunks of encoded data (if you ask for it to be
     * chunked)
     */
    static final byte[] CHUNK_SEPARATOR = "\n".getBytes();

    /**
     * The bsae length
     */
    static final int BASELENGTH = 255;

    /**
     * Lookup length
     */
    static final int LOOKUPLENGTH = 64;

    /**
     * Used to calculate the number of bits in a byte.
     */
    static final int EIGHTBIT = 8;

    /**
     * Used when encoding something which has fewer than 24 bits
     */
    static final int SIXTEENBIT = 16;

    /**
     * Constant used to determine how many bits data contains
     */
    static final int TWENTYFOURBITGROUP = 24;

    /**
     * Used to get the number of Quadruples
     */
    static final int FOURBYTE = 4;

    /**
     * Used to test the sign of a byte
     */
    static final int SIGN = -128;

    /**
     * Byte used to pad output
     */
    static final byte PAD = (byte) '=';

    // Create arrays to hold the base64 characters and a
    // lookup for base64 chars
    private static byte[] base64Alphabet = new byte[BASELENGTH];
    private static byte[] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

    // Populating the lookup and character arrays
    static {
        for (int i = 0; i < BASELENGTH; i++) {
            base64Alphabet[i] = (byte) -1;
        }
        for (int i = 'Z'; i >= 'A'; i--) {
            base64Alphabet[i] = (byte) (i - 'A');
        }
        for (int i = 'z'; i >= 'a'; i--) {
            base64Alphabet[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '9'; i >= '0'; i--) {
            base64Alphabet[i] = (byte) (i - '0' + 52);
        }

        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = 0; i <= 25; i++) {
            lookUpBase64Alphabet[i] = (byte) ('A' + i);
        }

        for (int i = 26, j = 0; i <= 51; i++, j++) {
            lookUpBase64Alphabet[i] = (byte) ('a' + j);
        }

        for (int i = 52, j = 0; i <= 61; i++, j++) {
            lookUpBase64Alphabet[i] = (byte) ('0' + j);
        }

        lookUpBase64Alphabet[62] = (byte) '+';
        lookUpBase64Alphabet[63] = (byte) '/';
    }

    /**
     * Decodes Base64 data into octects
     * 
     * @param base64Data
     *            Byte array containing Base64 data
     * @return Array containing decoded data.
     */
    public static byte[] decodeBase64(byte[] base64Data) {

        // RFC 2045 requires that we discard ALL non-Base64 characters
        base64Data = discardNonBase64(base64Data);

        // handle the edge case, so we don't have to worry about it later
        if (base64Data.length == 0) {
            return new byte[0];
        }

        int numberQuadruple = base64Data.length / FOURBYTE;
        byte decodedData[] = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = 0;
        {
            // this sizes the output array properly - rlw
            int lastData = base64Data.length;
            // ignore the '=' padding
            while (base64Data[lastData - 1] == PAD) {
                if (--lastData == 0) {
                    return new byte[0];
                }
            }
            decodedData = new byte[lastData - numberQuadruple];
        }

        for (int i = 0; i < numberQuadruple; i++) {
            dataIndex = i * 4;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex + 1]];

            if (marker0 != PAD && marker1 != PAD) {
                // No PAD e.g 3cQl
                b3 = base64Alphabet[marker0];
                b4 = base64Alphabet[marker1];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                decodedData[encodedIndex + 2] = (byte) (b3 << 6 | b4);
            } else if (marker0 == PAD) {
                // Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
            } else if (marker1 == PAD) {
                // One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[marker0];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            }
            encodedIndex += 3;
        }
        return decodedData;
    }

    /**
     * Discards any characters outside of the base64 alphabet, per the requirements on page 25 of
     * RFC 2045 - "Any characters outside of the base64 alphabet are to be ignored in base64 encoded
     * data."
     * 
     * @param data
     *            The base-64 encoded data to groom
     * @return The data, less non-base64 characters (see RFC 2045).
     */
    static byte[] discardNonBase64(byte[] data) {

        byte groomedData[] = new byte[data.length];
        int bytesCopied = 0;

        for (int i = 0; i < data.length; i++) {
            if (isBase64(data[i])) {
                groomedData[bytesCopied++] = data[i];
            }
        }

        byte packedData[] = new byte[bytesCopied];

        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);

        return packedData;
    }

    /**
     * Discards any whitespace from a base-64 encoded block.
     * 
     * @param data
     *            The base-64 encoded data to discard the whitespace from.
     * @return The data, less whitespace (see RFC 2045).
     */
    static byte[] discardWhitespace(byte[] data) {

        byte groomedData[] = new byte[data.length];
        int bytesCopied = 0;

        for (int i = 0; i < data.length; i++) {
            switch (data[i]) {
                case (byte) ' ':
                case (byte) '\n':
                case (byte) '\r':
                case (byte) '\t':
                    break;
                default:
                    groomedData[bytesCopied++] = data[i];
            }
        }

        byte packedData[] = new byte[bytesCopied];

        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);

        return packedData;
    }

    /**
     * Encodes binary data using the base64 algorithm (this does not "chunk" the output).
     * 
     * @param binaryData
     *            binary data to encode
     * @return Base64 characters
     */
    public static byte[] encodeBase64(byte[] binaryData) {

        return (encodeBase64(binaryData, false));
    }

    /**
     * Encodes hex octects into Base64.
     * 
     * @param binaryData
     *            Array containing binary data to encode.
     * @param isChunked
     *            if isChunked is true this encoder will chunk the base64 output into 76 character
     *            blocks
     * @return Base64-encoded data.
     */
    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {

        int lengthDataBits = binaryData.length * EIGHTBIT;
        int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
        int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
        byte encodedData[] = null;
        int encodedDataLength = 0;
        int nbrChunks = 0;

        if (fewerThan24bits != 0) {
            // data not divisible by 24 bit
            encodedDataLength = (numberTriplets + 1) * 4;
        } else {
            // 16 or 8 bit
            encodedDataLength = numberTriplets * 4;
        }

        // If the output is to be "chunked" into 76 character sections,
        // for compliance with RFC 2045 MIME, then it is important to
        // allow for extra length to account for the separator(s)
        if (isChunked) {

            nbrChunks = (CHUNK_SEPARATOR.length == 0 ? 0 : (int) Math
                    .ceil((float) encodedDataLength / CHUNK_SIZE));
            encodedDataLength += nbrChunks * CHUNK_SEPARATOR.length;
        }

        encodedData = new byte[encodedDataLength];

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        int nextSeparatorIndex = CHUNK_SIZE;
        int chunksSoFar = 0;

        // log.debug("number of triplets = " + numberTriplets);
        for (i = 0; i < numberTriplets; i++) {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];

            // log.debug("b1= " + b1 +", b2= " + b2 + ", b3= " + b3);

            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            // log.debug( "val2 = " + val2 );
            // log.debug( "k4 = " + (k<<4) );
            // log.debug( "vak = " + (val2 | (k<<4)) );
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[(l << 2) | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3f];

            encodedIndex += 4;

            // If we are chunking, let's put a chunk separator down.
            if (isChunked) {
                // this assumes that CHUNK_SIZE % 4 == 0
                if (encodedIndex == nextSeparatorIndex) {
                    System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedIndex,
                            CHUNK_SEPARATOR.length);
                    chunksSoFar++;
                    nextSeparatorIndex = (CHUNK_SIZE * (chunksSoFar + 1))
                            + (chunksSoFar * CHUNK_SEPARATOR.length);
                    encodedIndex += CHUNK_SEPARATOR.length;
                }
            }
        }

        // form integral number of 6-bit groups
        dataIndex = i * 3;

        if (fewerThan24bits == EIGHTBIT) {
            b1 = binaryData[dataIndex];
            k = (byte) (b1 & 0x03);
            // log.debug("b1=" + b1);
            // log.debug("b1<<2 = " + (b1>>2) );
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        } else if (fewerThan24bits == SIXTEENBIT) {

            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = PAD;
        }

        if (isChunked) {
            // we also add a separator to the end of the final chunk.
            if (chunksSoFar < nbrChunks) {
                System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedDataLength
                        - CHUNK_SEPARATOR.length, CHUNK_SEPARATOR.length);
            }
        }

        return encodedData;
    }

    /**
     * Encodes binary data using the base64 algorithm and chunks the encoded output into 76
     * character blocks
     * 
     * @param binaryData
     *            binary data to encode
     * @return Base64 characters chunked in 76 character blocks
     */
    public static byte[] encodeBase64Chunked(byte[] binaryData) {

        return (encodeBase64(binaryData, true));
    }

    /**
     * This array tests a given byte array to see if it contains only valid characters within the
     * Base64 alphabet.
     * 
     * @param arrayOctect
     *            byte array to test
     * @return true if all bytes are valid characters in the Base64 alphabet or if the byte array is
     *         empty; false, otherwise
     */
    public static boolean isArrayByteBase64(byte[] arrayOctect) {

        arrayOctect = discardWhitespace(arrayOctect);

        int length = arrayOctect.length;
        if (length == 0) {
            // shouldn't a 0 length array be valid base64 data?
            // return false;
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!isBase64(arrayOctect[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBase64(byte octect) {

        if (octect == PAD) {
            return true;
        } else if (base64Alphabet[octect] == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * ==============================================================================================
     * URL Encode and Decode
     * =============================================================================================
     */
    // /**
    // * The <code>String</code> encoding used for decoding and encoding.
    //     */
//    private final static String DEFAULT_ENCODING = "US-ASCII";

    /**
     * BitSet of www-form-url safe characters.
     */
    protected static final BitSet WWW_FORM_URL = new BitSet(256);

    // Static initializer for www_form_url
    static {
        // alpha characters
        for (int i = 'a'; i <= 'z'; i++) {
            WWW_FORM_URL.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            WWW_FORM_URL.set(i);
        }
        // numeric characters
        for (int i = '0'; i <= '9'; i++) {
            WWW_FORM_URL.set(i);
        }
        // special chars
        WWW_FORM_URL.set('-');
        WWW_FORM_URL.set('_');
        WWW_FORM_URL.set('.');
        WWW_FORM_URL.set('*');
        // blank to be replaced with +
        WWW_FORM_URL.set(' ');
    }

    /**
     * Encodes an array of bytes into an array of URL safe 7-bit characters. Unsafe characters are
     * escaped.
     * 
     * @param urlsafe
     *            bitset of characters deemed URL safe
     * @param pArray
     *            array of bytes to convert to URL safe characters
     * @return array of bytes containing URL safe characters
     */
    public static final byte[] encodeUrl(BitSet urlsafe, byte[] pArray) {

        if (pArray == null) {
            return null;
        }
        if (urlsafe == null) {
            urlsafe = WWW_FORM_URL;
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int i = 0; i < pArray.length; i++) {
            int b = pArray[i];
            if (b >= 0 && urlsafe.get(b)) {
                if (b == ' ') {
                    b = '+';
                }
                buffer.write(b);
            } else {
                buffer.write('%');
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
                buffer.write(hex1);
                buffer.write(hex2);
            }
        }
        return buffer.toByteArray();
    }

    /**
     * Decodes an array of URL safe 7-bit characters into an array of original bytes. Escaped
     * characters are converted back to their original representation.
     * 
     * @param pArray
     *            array of URL safe characters
     * @return array of original bytes
     * @throws DecoderException
     *             Thrown if URL decoding is unsuccessful
     */
    public static final byte[] decodeUrl(byte[] pArray) throws DecoderException {

        if (pArray == null) {
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int i = 0; i < pArray.length; i++) {
            int b = pArray[i];
            if (b == '+') {
                buffer.write(' ');
            } else if (b == '%') {
                try {
                    int u = Character.digit((char) pArray[++i], 16);
                    int l = Character.digit((char) pArray[++i], 16);
                    if (u == -1 || l == -1) {
                        throw new DecoderException("Invalid URL encoding");
                    }
                    buffer.write((char) ((u << 4) + l));
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DecoderException("Invalid URL encoding");
                }
            } else {
                buffer.write(b);
            }
        }
        return buffer.toByteArray();
    }

}
