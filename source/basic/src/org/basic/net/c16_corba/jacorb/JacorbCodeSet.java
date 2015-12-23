package org.basic.net.c16_corba.jacorb;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.jacorb.orb.giop.CodeSet;

public class JacorbCodeSet {

    public static void main(String[] args) {

        System.out.println("file.encoding:" + System.getProperty("file.encoding"));
        CodeSet.main(args);
        System.setProperty("file.encoding", "ISO-8859-1");
        CodeSet.main(args);
        
        System.out.println(java.nio.charset.Charset.defaultCharset());
        
        String defaultIOEncoding = (new OutputStreamWriter(new ByteArrayOutputStream ())).getEncoding();
        System.out.println(defaultIOEncoding);

    }
}
