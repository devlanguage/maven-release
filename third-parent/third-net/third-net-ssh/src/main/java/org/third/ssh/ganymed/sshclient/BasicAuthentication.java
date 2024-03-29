package org.third.ssh.ganymed.sshclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class BasicAuthentication {
    public static void main(String[] args) {

        String hostname = "127.0.0.1";
        String username = "ygong";
        String password = "Mecury00";

        try {
            /* Create a connection instance */

            ch.ethz.ssh2.Connection conn = new Connection(hostname);

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

            Session sess = conn.openSession();

            String commmand = "uname -a && date && uptime && who";
            System.out.print("-> " + commmand);
            sess.execCommand(commmand);
            System.out.println();

            /*
             * This basic example does not handle stderr, which is sometimes dangerous (please read the FAQ).
             */
            InputStream stdout = new StreamGobbler(sess.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            /* Show exit status, if available (otherwise "null") */

            System.out.println("ExitCode: " + sess.getExitStatus());

            /* Close this session */

            sess.close();

            /* Close the connection */

            conn.close();

        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }
}
