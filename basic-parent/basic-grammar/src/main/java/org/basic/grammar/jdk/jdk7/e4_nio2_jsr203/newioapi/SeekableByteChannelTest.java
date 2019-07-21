package org.basic.grammar.jdk.jdk7.e4_nio2_jsr203.newioapi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 */
public class SeekableByteChannelTest {
    @Test
    public void SeekableByteChannel() throws Exception {
        SeekableByteChannel channel = null;
        try {
            channel = java.nio.channels.FileChannel.open(
                    Paths.get(SeekableByteChannelTest.class.getResource("FileChannelTest.txt").toURI()), StandardOpenOption.READ);
            ByteBuffer byteByffer = ByteBuffer.allocate(4096);

            System.out.println("File size: " + channel.size());
            String encoding = System.getProperty("file.encoding");

            while (channel.read(byteByffer) > 0) {
                byteByffer.rewind();

                byte[] bytearray = new byte[byteByffer.remaining()];
                byteByffer.get(bytearray);
                System.out.print(new String(bytearray));

                byteByffer.flip();

                System.out.println("Current position : " + channel.position());
            }
        } catch (IOException e) {
            System.out.println("Expection when reading : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.close();
            }
        }

    }
}
