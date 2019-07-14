/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.insidejvm.hello.JvmConstants.java is created on 2008-2-26
 */
package org.basic.grammar.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Logger;

/**
 * 
 */
public class JvmConstants extends ClassLoader {

	public final static String WORKSPACE_HOME = "C:\\Software\\eclipse3.2.2\\workspace";
	public final static String PROJECT_NAME = "jvm";
	public final static String JVM_RESOURCES1 = WORKSPACE_HOME + "/" + PROJECT_NAME + "/resources1";
	public final static String JVM_RESOURCES2 = WORKSPACE_HOME + "/" + PROJECT_NAME + "/resources2";

}

class AnotherClassLoader extends ClassLoader {

	private String baseDir;
	private static final Logger LOG = Logger.getLogger(AnotherClassLoader.class.getName());

	public AnotherClassLoader(ClassLoader parent, String baseDir) {

		super(parent);
		this.baseDir = baseDir;
	}

	protected Class findClass(String name) throws ClassNotFoundException {

		LOG.finer("findClass " + name);
		byte[] bytes = loadClassBytes(name);
		Class theClass = defineClass(name, bytes, 0, bytes.length);
		// A
		if (theClass == null)
			throw new ClassFormatError();
		return theClass;
	}

	private byte[] loadClassBytes(String className) throws ClassNotFoundException {

		try {
			String classFile = getClassFile(className);
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
		} catch (IOException fnfe) {
			throw new ClassNotFoundException(className);
		}
	}

	private String getClassFile(String name) {

		StringBuffer sb = new StringBuffer(baseDir);
		name = name.replace('.', File.separatorChar) + ".class";
		sb.append(File.separator + name);
		return sb.toString();

	}

	/***************************************************************************
	 * Define the getResource
	 **************************************************************************/
	// public URL getResource(String name) {
	//
	// URL url;
	// if (parent != null) {
	// url = parent.getResource(name);
	// } else {
	// url = getBootstrapResource(name);
	// }
	// if (url == null) {
	// url = findResource(name);
	// // 这里
	// }
	// return url;
	// }

//    public java.net.URL getResource(String name) {
//
//        name = resolveName(name);
//        ClassLoader cl = getClassLoader0();
//
//        // 这里
//        if (cl == null) { // A system class. return
//            ClassLoader.getSystemResource(name);
//        }
//        return cl.getResource(name);
//    }

	protected URL findResource(String name) {

		LOG.finer("findResource " + name);
		try {
			URL url = super.findResource(name);
			if (url != null)
				return url;

			url = new URL("file:///" + converName(name));
			// �?化处理，�?有资源从文件系统中获�?
			return url;
		} catch (MalformedURLException mue) {
			LOG.severe("findResource");
			return null;
		}
	}

	private String converName(String name) {

		StringBuffer sb = new StringBuffer(baseDir);
		name = name.replace('.', File.separatorChar);
		sb.append(File.separator + name);
		return sb.toString();
	}
}