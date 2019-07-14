package org.basic.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.basic.common.bean.CommonLogger;

public class StringEncryptHelper {

    /*
     * The default encrpytion key which contain 8 chars
     */
    private static String defaultKey = "SNMP_NBI";

    private Cipher encryptCipher = null;

    private Cipher decryptCipher = null;

    /**
     * default constructor
     * 
     * @throws Exception
     */
    public StringEncryptHelper() {

//        this(defaultKey);
    }

    /**
     * String constructor make this constructor private temporarily
     * 
     * @param strKey
     * @throws Exception
     */
//    private StringEncryptHelper(String strKey) {
//
//        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
//            Key key = generateKey(strKey.getBytes());
//
//            encryptCipher = Cipher.getInstance("DES");
//            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
//
//            decryptCipher = Cipher.getInstance("DES");
//            decryptCipher.init(Cipher.DECRYPT_MODE, key);
//        } catch (Exception exp) {
//            CommonLogger.getLogger(StringEncryptHelper.class).log(CommonLogger.ERROR, "StringEncryptHelper",
//                    exp.getMessage(), exp);
//        }
//    }

    /**
     * encrypt method
     * 
     * @param input
     *            String to be encrypted
     * @return String after encrypted
     * @throws Exception
     */
    public String encrypt(String input) {

        try {
            return byteArr2HexStr(encrypt(input.getBytes()));
        } catch (Exception exp) {
            CommonLogger.getLogger(StringEncryptHelper.class).log(CommonLogger.ERROR, "encrypt", exp.getMessage(), exp);
            return input;
        }
    }

    /**
     * decrypt method
     * 
     * @param input
     *            String to be decrypted
     * @returnString after decrypted
     * @throws Exception
     */
    public String decrypt(String input) {

        try {
            return new String(decrypt(hexStr2ByteArr(input)));
        } catch (Exception exp) {
            CommonLogger.getLogger(StringEncryptHelper.class).log(CommonLogger.ERROR, "decrypt", exp.getMessage(), exp);
            return input;
        }
    }

    /**
     * encrypt byte array
     * 
     * @param arrB
     * @return
     * @throws Exception
     */
    private byte[] encrypt(byte[] arrB) throws Exception {

        return encryptCipher.doFinal(arrB);
    }

    /**
     * decrypt byte array
     * 
     * @param arrB
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte[] arrB) throws Exception {

        return decryptCipher.doFinal(arrB);
    }

    /**
     * byte array to hex string
     * 
     * @param arrB
     * @return
     * @throws Exception
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {

        int iLen = arrB.length;

        // 1 byte is described with 2 hex chars, so the string length is double to array length
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * hex string to byte array
     * 
     * @param strIn
     * @return
     * @throws Exception
     */
    public static byte[] hexStr2ByteArr(String input) throws Exception {

        byte[] arrB = input.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * generate key from string
     * 
     * @param arrBTmp
     *            , byte array of string
     * @return
     * @throws Exception
     */
    private Key generateKey(byte[] arrBTmp) {

        // init 8 empty byte array
        byte[] arrB = new byte[8];

        // init byte array to generate key
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // generate Key
        Key key = new SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * for test
     * 
     * @param args
     */
    public static void main(String[] args) {

        try {
            String test = "1Hh123JJJJJJJj#^@$";

            StringEncryptHelper des = new StringEncryptHelper();
            System.out.println("String before encryption: " + test);
            System.out.println("String after encryption: " + des.encrypt(test));
            System.out.println("String after decryption: " + des.decrypt(des.encrypt(test)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
