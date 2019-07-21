package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.SecurityContextConstraints;
import io.fabric8.openshift.api.model.SecurityContextConstraintsBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class SecurityContextConstraintExample {

    private static final Logger logger = LoggerFactory.getLogger(SecurityContextConstraintExample.class);

    //You need to be login as admin on OpenShift for this Example
    //command for that is
    //oc login -u system:admin

    public static void main(String[] args) throws InterruptedException {

        try (OpenShiftClient client = new DefaultOpenShiftClient()) {

          SecurityContextConstraints scc = new SecurityContextConstraintsBuilder()
            .withNewMetadata().withName("scc").endMetadata()
            .withAllowPrivilegedContainer(true)
            .withNewRunAsUser()
            .withType("RunAsAny")
            .endRunAsUser()
            .withNewSeLinuxContext()
            .withType("RunAsAny")
            .endSeLinuxContext()
            .withNewFsGroup()
            .withType("RunAsAny")
            .endFsGroup()
            .withNewSupplementalGroups()
            .withType("RunAsAny")
            .endSupplementalGroups()
            .addToUsers("admin")
            .addToGroups("admin-group")
            .build();

          log("Created SecurityContextConstraints", client.securityContextConstraints().create(scc));

          client.close();

        } catch (KubernetesClientException e) {
          logger.error(e.getMessage(), e);
        }
    }

    private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

    private static void log(String action) {
    logger.info(action);
  }
}
