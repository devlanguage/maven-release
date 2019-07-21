package org.basic.net.c20_jmx.jdmk.chat.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 */
public class ServerSideProcessor implements Runnable {

    Socket client;

    public ServerSideProcessor(Socket client) {

        this.client = client;
    }

    public void run() {

        DataOutputStream dos = null;
        try {
            System.out.println(client.getInetAddress() + "Connect to server");
            InputStream in = client.getInputStream();

            DataInputStream dis = new DataInputStream(in);
            OutputStream out = client.getOutputStream();
            dos = new DataOutputStream(out);
            SocketServer.CLIENT_OUTPUT_STREAM.add(dos);
            while (true) {
                String msg = dis.readUTF();
                System.out.println("ServerReceived: " + msg);
                // msg = StringUtil.streamToStringBuffer(dis).toString();
                for (int i = 0; i < SocketServer.CLIENT_OUTPUT_STREAM.size(); i++) {
                    DataOutputStream d = (DataOutputStream) SocketServer.CLIENT_OUTPUT_STREAM
                            .elementAt(i);
                    d.writeUTF("ServerSend: "+client.getInetAddress() + ":" + msg);
                    // d.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(client.getInetAddress() + "");
            SocketServer.CLIENT_OUTPUT_STREAM.removeElement(dos);
            try {
                client.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
    }

}
