package org.basic.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class CopyFile {

    public static void main(String[] args) {

        String inFile = "nio_input.txt";
        String outFile = "nio_output.txt";
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            // 获取源文件和目标文件的输入输出流
            fin = new FileInputStream(inFile);
            fout = new FileOutputStream(outFile);
            // 获取输入输出通道
            java.nio.channels.FileChannel fcin = fin.getChannel();
            java.nio.channels.FileChannel fcout = fout.getChannel();
            // 创建缓冲区
            java.nio.ByteBuffer buffer = ByteBuffer.allocate(1024);
            int r = -1;
            while ((r = fcin.read(buffer)) != -1) {
                // 从输入通道中将数据读到缓冲区
                // read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
                // flip方法让缓冲区可以将新读入的数据写入另一个通道
//                limit = position;
//                position = 0;
//                mark = -1;
                buffer.flip();
                // 从输出通道中将数据写入缓冲区
                fcout.write(buffer);
//                position = 0;
//                limit = capacity;
//                mark = -1;
//                return this;
                buffer.clear();// clear方法重设缓冲区，使它可以接受读入的数据
            }
        } catch (Exception e) {

        }

    }
}
