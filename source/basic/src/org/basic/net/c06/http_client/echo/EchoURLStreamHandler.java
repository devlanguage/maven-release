package org.basic.net.c06.http_client.echo;

import java.net.*;
import java.io.*;

public class EchoURLStreamHandler extends URLStreamHandler {
    public int getDefaultPort() {
        return 8000;
    }

    protected URLConnection openConnection(URL url) throws IOException {
        return new EchoURLConnection(url);
    }
}
