package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.VersionInfo;

public class KubernetesVersionExample {
  private static Logger logger = LoggerFactory.getLogger(KubernetesVersionExample.class);

  public static void main(String args[]) {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try(final KubernetesClient client = new DefaultKubernetesClient(config)) {
      VersionInfo versionInfo = client.getVersion();

      log("Version details of this Kubernetes cluster :-");
      log("Major        : ", versionInfo.getMajor());
      log("Minor        : ", versionInfo.getMinor());
      log("GitVersion   : ", versionInfo.getGitVersion());
      log("BuildDate    : ", versionInfo.getBuildDate());
      log("GitTreeState : ", versionInfo.getGitTreeState());
      log("Platform     : ", versionInfo.getPlatform());
      log("GitVersion   : ", versionInfo.getGitVersion());
      log("GoVersion    : ", versionInfo.getGoVersion());
      log("GitCommit    : ", versionInfo.getGitCommit());
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
