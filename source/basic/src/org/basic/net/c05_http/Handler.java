package org.basic.net.c05_http;
import java.io.*;
import java.nio.channels.*;

public interface Handler {
    public void handle(SelectionKey key) throws IOException;
}


