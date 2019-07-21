package org.third.k8s.googlel.v5;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.util.Config;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * A simple example of how to parse a Kubernetes object.
 *
 * <p>Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.ParseExample"
 *
 * <p>From inside $REPO_DIR/examples
 */
public class ParseExample {
  public static void main(String[] args) throws IOException, ApiException, ClassNotFoundException {
    if (args.length < 2) {
      System.err.println("Usage: ParseExample <file-name> <class-name e.g. V1Pod>");
      System.exit(1);
    }
    ApiClient client = Config.defaultClient();
    FileReader json = new FileReader(args[0]);
    Object obj =
        client
            .getJSON()
            .getGson()
            .fromJson(json, Class.forName("io.kubernetes.client.models." + args[1]));

    String output = client.getJSON().getGson().toJson(obj);

    // Test round tripping...
    Object obj2 =
        client
            .getJSON()
            .getGson()
            .fromJson(
                new StringReader(output), Class.forName("io.kubernetes.client.models." + args[1]));

    String output2 = client.getJSON().getGson().toJson(obj2);

    // Validate round trip
    if (!output.equals(output2)) {
      System.err.println("Error, expected:\n" + output + "\nto equal\n" + output2);
      System.exit(2);
    }

    System.out.println(output);
  }
}
