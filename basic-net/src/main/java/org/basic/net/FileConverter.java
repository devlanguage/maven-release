package org.basic.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

/**
 * <pre>
 *
 * </pre>
 */
public class FileConverter {
    static final String src = "C:/software/dev/eclispe/java_proj/java_net_programming/sourcecode";
    static final String dest = "C:/software/dev/eclispe/java_proj/java_net_programming/dest";

    public static void main(String[] args) {
        File f = new File(src);
        iterateFile(f);
    }

    static final FileFilter filter = (f) -> {
        return f.isDirectory() || f.getName().endsWith(".java");
    };
    static final FileSystem fs = FileSystems.getDefault();
    static final byte[] buffer = new byte[512];

    private static void iterateFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles(filter);
            for (File f1 : files) {
                iterateFile(f1);
            }
        }
        else {
            BufferedReader ins = null;
            BufferedWriter out = null;
            try {
                String destFileName = f.getName();
                String destDirectoryName = dest + ""
                                + f.getAbsolutePath().substring(src.length(), f.getAbsolutePath().length() - destFileName.length());
                File destDirectory = new File(dest + "" + f.getParent().substring(src.length()));
                if (!destDirectory.exists()) {
                    destDirectory.mkdirs();
                }

                File destFile = new File(destDirectory, destFileName);
                ins = new BufferedReader(new InputStreamReader(new FileInputStream(f.getAbsolutePath()), "GBK"));
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8"));
                String line = null;
                while (null != (line = ins.readLine())) {
                    // if(destFileName.equals("EchoClient.java"))
                    if (line.startsWith("/****************************************************")) {
                        break;
                    }
                    out.write(line);
                    out.write("\r\n");
                    
                }
                System.out.println(destFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ins.close();
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
