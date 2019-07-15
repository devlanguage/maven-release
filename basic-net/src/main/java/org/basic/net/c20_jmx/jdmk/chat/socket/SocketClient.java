package org.basic.net.c20_jmx.jdmk.chat.socket;

/**
 * 
 */

public class SocketClient {

    public static void main(String args[]) throws Exception {

        new Thread(new ClientSideProcessor()).start();

        // try {
        // URL url = new URL("http://localhost:20000");
        // InputStream ins = url.openStream();
        // String httpIns = StringUtil.streamToStringBuffer(ins).toString();
        // System.out.println(httpIns);
        // ins.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

}
