package org.basic.common.util;

public class NumberUtil {

    public final static byte[] intToByteArray(int in) {

        byte[] byteArray = new byte[4];
        byteArray[3] = (byte) (in & 0xff);
        byteArray[2] = (byte) ((in >> 8) & 0xff);
        byteArray[1] = (byte) ((in >> 16) & 0xff);
        byteArray[0] = (byte) ((in >> 24) & 0xff);
        return byteArray;
    }

    public final static boolean isPositiveInteger(int intValue) {

        return intValue <= Integer.MAX_VALUE && intValue > 0;
    }

    /**
     * If param digit is composed fullly by the digit
     * 
     * @param digit
     * @return
     */
    public final static boolean isComposedByDigit(String digit) {

        return SystemUtil.isNullOrBlank(digit) ? false : digit.matches("[0-9]*");
    }
}
