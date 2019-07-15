package org.basic.common.util;


public class PasswordUtil {

//    private final static NbiLogger logger = NbiLogger.getLogger(PasswordUtil.class);

    /**
     * check the password is valid according to Req [164]SNMP NBI[1005] [FP4.2.1]
     * 
     * SNMP Mediation shall require a minimum 15 character passphrase with a mix of upper case
     * letters, lower case letters, numbers and special characters, including at least two of each
     * of the four types of characters.
     * 
     * Special Character : Non-alphabetic or non-numeric character, such as @, #, $, %, &, * and +.
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {

        // require a minimum 15 character
        if (password == null || password.length() < 15)
            return false;

        int countUpperCaseChar = 0;
        int countLowerCaseChar = 0;
        int countNumberChar = 0;
        int countSpecialChar = 0;

        char[] chars = password.toCharArray();
        for (int ndx = 0; ndx < chars.length; ndx++) {
            char ch = chars[ndx];

            if (Character.isUpperCase(ch))
                countUpperCaseChar++;
            else if (Character.isLowerCase(ch))
                countLowerCaseChar++;
            else if (Character.isDigit(ch))
                countNumberChar++;
            else
                countSpecialChar++;
        }

        // at least two of each of the four types of characters.
        if (countUpperCaseChar >= 2 && countLowerCaseChar >= 2 && countNumberChar >= 2
                && countSpecialChar >= 2)
            return true;

        return false;
    }
}
