package org.third.transport.ssh.ganymed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class TL1SessionWithTL1 {
    class SshReader implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.print((char) _bufin.read());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    class SshWriter implements Runnable {

        @Override
        public void run() {
            java.util.Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    String tl1Cmd = scanner.nextLine();
                    boolean result = sendData(tl1Cmd + ";\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected InputStream _in = null;
    protected OutputStream _out = null;
    protected BufferedReader _bufin = null;
    static final String hostname = "172.29.160.112";
    // static final String hostname = "172.29.157.86";
    static final int port = 3182;
    static final String username = "admin1";
    static final String password = "1Transport!";

    public static void main(String[] args) {

        TL1SessionWithTL1 s = new TL1SessionWithTL1();
        s.testTL1SessionWithoutPTY();

    }

    private void testTL1SessionWithoutPTY() {
        try {
            Connection conn = new Connection(hostname, port);
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");

            Session sess = conn.openSession();
            testPlainSshConnection(sess);

            // sess.requestDumbPTY();///pty-req: dumb
            // byte b1[] = new byte[] { 98, 0, 0, 0, 0, 0, 0, 0, 7, 112, 116, 121, 45, 114, 101, 113, 1, 0, 0, 0, 4,
            // 100,
            // 117, 109, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };
            // sshSession.cm.tm.sendMessage(b1);
            sess.startShell();

            startTest();

            Thread.sleep(1000000);
            sess.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }

    private void startTest() throws InterruptedException {
        new Thread(new SshWriter()).start();
        new Thread(new SshReader()).start();
    }

    public void consumeAllInput() throws IOException {

        int c;
        while (_bufin.ready() == true) {
            c = _bufin.read();
            System.out.print((char) c);
        }
    }

    public boolean sendData(String tl1CmdStr) {

        try {
            _out.write(tl1CmdStr.getBytes());
            _out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private void testPlainSshConnection(ch.ethz.ssh2.Session sshSession) throws IOException {
        _in = new ch.ethz.ssh2.StreamGobbler(sshSession.getStdout());
        _bufin = new BufferedReader(new InputStreamReader(_in));
        _out = sshSession.getStdin();

    }
}
