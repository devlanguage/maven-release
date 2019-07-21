package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.VersionInfo;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class OpenShiftVersionExample {
  private static Logger logger = LoggerFactory.getLogger(OpenShiftVersionExample.class);

  public static void main(String args[]) {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();

    try(final OpenShiftClient client = new DefaultOpenShiftClient(config)) {
      VersionInfo versionInfo = client.getVersion();

      log("Version details of this OpenShift cluster :-");
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
