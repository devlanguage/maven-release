package org.basic.io.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * Created on May 6, 2014, 3:45:24 PM
 */
public class ByteBufferTest {
    public static void main(String[] args) {
        try {
            testCharBuffer();
            testFileChannel();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static final void testCharBuffer() throws Exception {
        // Invariant: mark <= position <= limit <= capacity
        CharBuffer ct1 = CharBuffer.allocate(100);
        System.out.println(toString(ct1));
        // if position >= limit, exception will be thrown. after calling flip(), limit(), limit will be change. so
        // get/put/append may fail. use clear(), limit(x), or hasRemaining(), make sure read/write successful

        ct1.put(Messages.getString("ByteBufferTest.0")); //$NON-NLS-1$
        ct1.append(Messages.getString("ByteBufferTest.1")); // position will increment, but limit will not change. //$NON-NLS-1$
        ct1.append(Messages.getString("ByteBufferTest.2")); //$NON-NLS-1$
        ct1.mark(); // mark = position;
        ct1.reset();// position=mark
        ct1.append(Messages.getString("ByteBufferTest.3")); //$NON-NLS-1$
        // ct1.flip(); // limit = position; position = 0; mark = -1;
        // ct1.limit(1);// if 0=<newLimit<capacity,limit = newLimit, pos=mix(limit,pos) otherwise, exception
        // ct1.rewind();// position = 0; mark = -1;
        // ct1.toString();// [position, limit]
        // ct1.put("x"); // position=position+"x".length
        ct1.get();// Get and increment the position.
        ct1.clear();// position = 0; limit = capacity; mark = -1;
        ct1.remaining();// limit - position

        System.out.println(ct1.toString());
        System.out.println(toString(ct1));

        ByteBuffer bt2 = ByteBuffer.allocateDirect(100); // Without backing array
        ByteBuffer bt1 = ByteBuffer.allocate(100);

    }

    static final void testFileChannel() throws Exception {
        ByteBuffer bt1 = ByteBuffer.allocateDirect(100); // Without backing array
        ByteBuffer bt2 = ByteBuffer.allocateDirect(100); // Without backing array

        java.io.RandomAccessFile aFile = new RandomAccessFile(
                Messages.getString("ByteBufferTest.4"), Messages.getString("ByteBufferTest.5")); //$NON-NLS-1$ //$NON-NLS-2$
        FileChannel fileChannel = aFile.getChannel();
        fileChannel.read(bt1);

        String newData = Messages.getString("ByteBufferTest.6") + System.currentTimeMillis(); //$NON-NLS-1$
        bt2.clear();
        bt2.put(newData.getBytes());
        bt2.flip();
        while (bt2.hasRemaining()) {
            fileChannel.write(bt2);
        }
    }

    public static final String toString(CharBuffer ct1) {
        return Messages.getString("ByteBufferTest.7") + ct1.position() + Messages.getString("ByteBufferTest.8") + ct1.limit() + Messages.getString("ByteBufferTest.9") + ct1.remaining(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
