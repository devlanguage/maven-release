package org.basic.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import org.basic.common.bean.CommonLogger;

public class FileUtil {

    private final static CommonLogger logger = CommonLogger.getLogger(FileUtil.class);

    // public final static boolean ftpList(String server, String user, String
    // password, String path) {
    //
    // try {
    // FTPClient ftpClient = new FTPClient(server);
    // ftpClient.login(user, password);
    //
    // if (path.length() != 0)
    // ftpClient.chdir(path);
    //
    // ftpClient.quit();
    // } catch (IOException ex) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.ftpList",
    // "Failed to change to directory: " + path, ex);
    // return false;
    // } catch (FTPException ftpe) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.ftpList",
    // "Failed to change to directory: " + path, ftpe);
    // return false;
    // }
    //
    // return true;
    // }
    //
    // public final static boolean download(String server, String user, String
    // password, String path,
    // String filename) {
    //
    // try {
    // FTPClient ftpClient = new FTPClient(server);
    // ftpClient.login(user, password);
    //
    // if (path.length() != 0)
    // ftpClient.chdir(path);
    //
    // ftpClient.setType(FTPTransferType.BINARY);
    //
    // ftpClient.get(filename, filename);
    //
    // ftpClient.quit();
    // } catch (IOException ex) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.download",
    // "Failed to download file from : "
    // + path + "/" + filename, null);
    // return false;
    // } catch (FTPException ftpe) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.download",
    // "Failed to download file from : "
    // + path + "/" + filename, null);
    // return false;
    // }
    //
    // return true;
    // }
    //
    // public final static boolean upload(String server, String user, String
    // password, String path,
    // String filename) {
    //
    // try {
    // FTPClient ftpClient = new FTPClient(server);
    // ftpClient.login(user, password);
    //
    // if (path.length() != 0)
    // ftpClient.chdir(path);
    //
    // ftpClient.setType(FTPTransferType.BINARY);
    //
    // ftpClient.put(filename, filename);
    //
    // ftpClient.quit();
    // } catch (IOException ex) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.upload", "Failed to upload file ",
    // null);
    // return false;
    // } catch (FTPException ftpe) {
    // logger.log(BasicLogger.ERROR, "FtpUtil.upload", "Failed to upload file ",
    // null);
    // return false;
    // }
    //
    // return true;
    // }
    static final String src = "C:/software/3rd/misc/ShoppingBook/test";
    static final String dest = "C:/software/3rd/misc/ShoppingBook/dest";

    public static void main(String[] args) {
        File f = new File(src);
        iterateFile(f);
    }

    static final FileFilter filter = (f) -> {
        // return f.isDirectory() || f.getName().endsWith(".java");
        return true;
    };
    static final FileSystem fs = FileSystems.getDefault();
    static final byte[] buffer = new byte[512];

    public static final String guessFileEncoding(File file) throws FileNotFoundException, IOException {
        // BufferedInputStream bis = new BufferedInputStream(new
        // FileInputStream(file));
        //
        // // byte[] first3Bytes = new byte[3];
        // // bis.mark(0);
        // // int read = bis.read(first3Bytes, 0, 3);
        //
        // int p = (bis.read() << 8) + bis.read();
        // String code = null;
        // // 其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        // switch (p) {
        // case 0xefbb: // ef bb bf
        // code = "UTF-8"; // UTF-8
        // break;
        // case 0xfffe:
        // code = "Unicode"; // Unicode
        // break;
        // case 0xfeff:
        // code = "UTF-16BE";// Unicode big endian
        // break;
        // case 0x5c75:
        // code = "ANSI|ASCII";
        // break;
        // default:
        // code = "GBK";
        // }
        //
        // return code;
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            }
            else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            }
            else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                        // 也有可能出错，但是几率较小
                    }
                    else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            }
                            else
                                break;
                        }
                        else
                            break;
                    }
                }
                System.out.println(loc + " " + Integer.toHexString(read));
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;

    }

    private static void iterateFile(File srcFile) {
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles(filter);
            for (File f1 : files) {
                iterateFile(f1);
            }
        }
        else {
            BufferedReader ins = null;
            BufferedWriter out = null;
            try {
                String destFileName = srcFile.getName();
                String destDirectoryName = dest + ""
                                + srcFile.getAbsolutePath().substring(src.length(), srcFile.getAbsolutePath().length() - destFileName.length());
                File destDirectory = new File(dest + "" + srcFile.getParent().substring(src.length()));
                if (!destDirectory.exists()) {
                    destDirectory.mkdirs();
                }

                File destFile = new File(destDirectory, destFileName);
                ins = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile.getAbsolutePath()), guessFileEncoding(srcFile)));// "GBK"
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
                System.out.println("src_Encoding=" + guessFileEncoding(srcFile) + ": " + destFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ins != null)
                        ins.close();
                    if (out != null)
                        out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
