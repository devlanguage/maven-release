package org.basic.net.c20_jmx.jdmk.chat.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 */
class ProcessInput extends Thread {

    DataInputStream dis;

    public ProcessInput(DataInputStream dis) {

        this.dis = dis;
    }

    public void run() {

        while (true) {
            try {
                String msg = dis.readUTF();
                System.out.println("ClientReceived: "+msg);
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class ClientSideProcessor implements Runnable {

    public void run() {

        try {
            Socket server = new Socket("127.0.0.1", 2000);
            InputStream in = server.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            ProcessInput p = new ProcessInput(dis);
            p.start();
            OutputStream out = server.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            while (true) {
                String userInput = getUserInput();
                dos.writeUTF("ClientSend: "+userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getUserInput() {

        System.out.print("发�?信息:");
        InputStreamReader inr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(inr);
        try {
            String s = br.readLine();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
