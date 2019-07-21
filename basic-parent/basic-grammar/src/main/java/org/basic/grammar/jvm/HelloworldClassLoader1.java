package org.basic.grammar.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 
 */
public class HelloworldClassLoader1 extends ClassLoader {

    private String userDefinedClassBaseDir;

    public HelloworldClassLoader1(ClassLoader parent) {

        this(parent, JvmConstants.JVM_RESOURCES1);
    }

    public HelloworldClassLoader1(ClassLoader parent, String baseDir) {

        super(parent);
        this.userDefinedClassBaseDir = baseDir == null ? JvmConstants.JVM_RESOURCES1 : baseDir;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {

        byte[] classBytes = null;
        classBytes = parseClass(className);
        if (classBytes == null) {
            throw new ClassFormatError();
        }
        Class foundClass = defineClass(className, classBytes, 0, classBytes.length);

        return foundClass;
    }

    private byte[] parseClass(String className) throws ClassNotFoundException {

        String classFile = userDefinedClassBaseDir + File.separatorChar + className.replace('.', File.separatorChar)
                + ".class";
        try {
            FileInputStream fis = new FileInputStream(classFile);
            FileChannel fileC = fis.getChannel();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel outC = Channels.newChannel(baos);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = fileC.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outC.write(buffer);
                buffer.clear();
            }
            fis.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(className);

        }

    }

    @Override
    protected URL findResource(String resourceName) {

        try {
            URL url = super.findResource(resourceName);
            if (url != null)
                return url;
            url = new URL("file:///" + userDefinedClassBaseDir + File.separatorChar
                    + resourceName.replace('.', File.separatorChar) + "File.separatorChar" + resourceName);
            // �?化处理，�?有资源从文件系统中获�?
            return url;
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
            return null;
        }

    }
}
