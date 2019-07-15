package org.basic.io.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class ZipJarTester {

    public static void main(String[] args) {

        InputStream input = null;
        try {
            input = ZipJarTester.class.getResourceAsStream("oscache-2.4.1.zip");
            JarInputStream jarInput = new JarInputStream(input);
            ZipEntry entry = null;
            while (null != (entry = jarInput.getNextEntry())) {
                
                System.out.print(entry.isDirectory());
                System.out.print(entry.getComment());
                System.out.print(entry.getCompressedSize());
                System.out.print(entry.getCrc());
                System.out.print(entry.getMethod());
                System.out.print(entry.getName());
                System.out.print(entry.getTime());
                System.out.print(entry.getExtra());
                System.out.print(entry.getClass());
                System.out.println();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
