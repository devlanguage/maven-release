package org.basic.jdk.core.pattern.behavioral.Iterator.test1;

/**
 * Data stored in a vector
 */
import java.io.*;
import java.util.*;

public class DataVector implements Aggregate {

    private Vector<String> data = new Vector<String>();

    public DataVector(InputStream ins) {

        try {
            BufferedReader f = new BufferedReader(new InputStreamReader(ins));
            String s = f.readLine();
            while (s != null) {
                if (s.trim().length() > 0) {
                    data.add(s);
                }
                s = f.readLine();
            }
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can not find such file !");
        } catch (IOException e) {
            System.out.println("I/O Error !");
            System.exit(0);
        }
    }

    public Iterator createIterator() {

        return new VectorIterator(data);
    }

}