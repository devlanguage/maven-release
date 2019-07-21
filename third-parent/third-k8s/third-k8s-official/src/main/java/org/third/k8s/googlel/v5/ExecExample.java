package org.third.k8s.googlel.v5;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.Exec;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple example of how to use the Java API
 *
 * <p>Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.ExecExample"
 *
 * <p>From inside $REPO_DIR/examples
 */
public class ExecExample {
  public static void main(String[] args) throws IOException, ApiException, InterruptedException {
    String podName = "nginx-4217019353-k5sn9";
    String namespace = "default";
    List<String> commands = new ArrayList<>();

    int len = args.length;
    if (len >= 1) podName = args[0];
    if (len >= 2) namespace = args[1];
    for (int i = 2; i < len; i++) {
      commands.add(args[i]);
    }

    ApiClient client = Config.defaultClient();
    Configuration.setDefaultApiClient(client);

    Exec exec = new Exec();
    boolean tty = System.console() != null;
    // final Process proc = exec.exec("default", "nginx-4217019353-k5sn9", new String[]
    //   {"sh", "-c", "echo foo"}, true, tty);
    final Process proc =
        exec.exec(
            namespace,
            podName,
            commands.isEmpty()
                ? new String[] {"sh"}
                : commands.toArray(new String[commands.size()]),
            true,
            tty);

    Thread in =
        new Thread(
            new Runnable() {
              public void run() {
                try {
                  ByteStreams.copy(System.in, proc.getOutputStream());
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
              }
            });
    in.start();

    Thread out =
        new Thread(
            new Runnable() {
              public void run() {
                try {
                  ByteStreams.copy(proc.getInputStream(), System.out);
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
              }
            });
    out.start();

    proc.waitFor();

    // wait for any last output; no need to wait for input thread
    out.join();

    proc.destroy();

    System.exit(proc.exitValue());
  }
}
