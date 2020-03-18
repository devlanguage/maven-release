package org.basic.grammar.jdk.jdk.scan;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

    public static ArrayList<Class<?>> getAllClassByInterface(Class<?> intfClass) {
        ArrayList<Class<?>> list = new ArrayList<>();
        if (intfClass.isInterface()) {
            try {
                ArrayList<Class<?>> allClass = getAllClass(intfClass.getPackage().getName());
                // Check the found class under this package implements the predefined interface,
                // excluding itself
                for (int i = 0; i < allClass.size(); i++) {
                    // Check if it is the same interface
                    if (intfClass.isAssignableFrom(allClass.get(i))) {
                        if (!intfClass.equals(allClass.get(i))) {
                            // Exclude itself
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("出现异常{}", e.getMessage());
                throw new RuntimeException("出现异常" + e.getMessage());
            }
        }
        LOG.info("class list size :" + list.size());
        return list;
    }

    /**
     * 从一个指定路径下查找所有的类
     *
     * @param packagename
     */
    private static ArrayList<Class<?>> getAllClass(String packagename) {

        LOG.info("packageName to search：" + packagename);
        List<String> classNameList = getClassName(packagename);
        ArrayList<Class<? extends Object>> list = new ArrayList<>();

        for (String className : classNameList) {
            try {
                list.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                LOG.error("load class from name failed:" + className + e.getMessage());
                throw new RuntimeException("load class from name failed:" + className + e.getMessage());
            }
        }
        LOG.info("find list size :" + list.size());
        return list;
    }

    /**
     * Finds all teh classes on specific package
     */
    public static List<String> getClassName(String packageName) {

        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            LOG.debug("file type : " + type);
            if (type.equals("file")) {
                String fileSearchPath = url.getPath();
                LOG.debug("fileSearchPath: " + fileSearchPath);
                fileSearchPath = fileSearchPath.substring(0, fileSearchPath.indexOf("/classes"));
                LOG.debug("fileSearchPath: " + fileSearchPath);
                fileNames = getClassNameByFile(fileSearchPath);
            } else if (type.equals("jar")) {
                try {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileNames = getClassNameByJar(jarFile, packagePath);
                } catch (java.io.IOException e) {
                    throw new RuntimeException("open Package URL failed：" + e.getMessage());
                }

            } else {
                throw new RuntimeException("file system not support! cannot load MsgProcessor！");
            }
        }
        return fileNames;
    }

    /**
     * Finds all teh classes on specific path
     */
    private static List<String> getClassNameByFile(String filePath) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameByFile(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9,
                            childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    private static List<String> getClassNameByJar(JarFile jarFile, String packagePath) {
        List<String> myClassName = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                // LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                    // LOG.debug("Find Class :"+entryName);
                }
            }
        } catch (Exception e) {
            LOG.error("Some error happens:" + e.getMessage(), e);
            throw new RuntimeException("Some error happens:" + e.getMessage());
        }
        return myClassName;
    }
}
