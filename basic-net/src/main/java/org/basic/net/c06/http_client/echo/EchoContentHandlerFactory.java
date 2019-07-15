package org.basic.net.c06.http_client.echo;

import java.net.*;

public class EchoContentHandlerFactory implements ContentHandlerFactory {
    public ContentHandler createContentHandler(String mimetype) {
        if (mimetype.equals("text/plain")) {
            return new EchoContentHandler();
        }
        else {
            return null;
        }
    }
}
