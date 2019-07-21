package org.basic.security.jaas.secret;

import java.io.File;

/**
 * Created on Apr 29, 2014, 2:13:14 PM
 */
public class CountFilesAction<T extends Integer> implements java.security.PrivilegedAction<T> {

    /*
     * (non-Javadoc)
     * 
     * @see java.security.PrivilegedAction#run()
     */
    public T run() {
        File f = new File(".");
        File[] files = f.listFiles();
        System.out.println("Filecount: "+files.length);
        return (T) new Integer(files.length);
    }
    
}
