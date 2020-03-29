package org.third.net.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpRequestHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        Headers reqHeader = t.getRequestHeaders();
        for (Object e : reqHeader.entrySet()) {
            System.out.println(e);
        }

        InputStream reqBody = t.getRequestBody();
        System.out.println(org.basic.common.util.StreamUtils.streamToString(reqBody));
        String response = "<h3>Happy New Year 2007! -- Chinajash</h3>";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
