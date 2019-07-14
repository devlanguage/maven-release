package org.basic.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringUtil {

    public static String streamToStringBuffer(InputStream ins) throws IOException {

        int BUFFER_SIZE = 512;
        int read = -1;
        byte[] cache = new byte[BUFFER_SIZE];
        StringBuilder sb = new StringBuilder();
        while ((read = ins.read(cache)) != -1) {
            sb.append(new String(cache, 0, read));
        }

        return sb.toString();
    }

    public static String streamToStringBuffer(InputStream ins, String charset) throws IOException {

        int BUFFER_SIZE = 512;
        int read = -1;
        byte[] cache = new byte[BUFFER_SIZE];
        StringBuilder sb = new StringBuilder();
        while ((read = ins.read(cache)) != -1) {
            sb.append(new String(cache, 0, read, charset));
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {

        InputStream fins = StringUtil.class.getResourceAsStream("readme.txt");
        String s = streamToStringBuffer(fins);
        System.out.println(s);
    }

}
