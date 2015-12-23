/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:59:47 AM Jan 8, 2015
 *
 *****************************************************************************
 */
package org.third.transport.ssh.ganymed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;

/**
 * Created on Jan 8, 2015, 9:59:47 AM
 */

public final class SshConsole {

    public static void main(String[] args) {

        String hostname = "172.29.160.118";
        String username = "root";
        String password = "root";
        ch.ethz.ssh2.Connection conn = null;
        ch.ethz.ssh2.Session sess = null;
        Scanner console = new Scanner(System.in);
        String shellCmd = null;
        try {
            /* Create a connection instance */

            conn = new Connection(hostname);

            /* Now connect */

            conn.connect();

            /*
             * Authenticate. If you get an IOException saying something like
             * "Authentication method password not supported by the server at this stage." then please check the FAQ.
             */

            boolean isAuthenticated = conn.authenticateWithPassword(username, password);

            if (isAuthenticated == false)
                throw new IOException("Authentication failed.");

            /* Create a session */

            sess = conn.openSession();
            sess.requestPTY("vt100", 80, 24, 640, 480, null);
            sess.startShell();
            // Thread.sleep(4000);

            shellCmd = "uname -a && date && uptime && who";
            System.out.print("-> " + shellCmd);
            // sess.execCommand(shellCmd);
            System.out.println();

            /*
             * This basic example does not handle stderr, which is sometimes dangerous (please read the FAQ).
             */
            InputStream stdout = new StreamGobbler(sess.getStdout());
            final BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        String line;
                        try {
                            line = br.readLine();
                            System.out.println(line+"\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
//                        System.out.println("ExitCode: " + sess.getExitStatus());
                    }
                }
            }).start();

            OutputStream stdIn = sess.getStdin();
            while (true) {
                    shellCmd = console.nextLine();
                    shellCmd = shellCmd+"\n";
                    stdIn.write(shellCmd.getBytes());
                    stdIn.flush();
                    // sess.execCommand(shellCmd);
//                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(2);
        } finally {
            sess.close();

            conn.close();
        }
    }
}