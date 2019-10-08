package org.third.fabric8.kubernetes;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.batch.Job;
import io.fabric8.kubernetes.api.model.batch.JobBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;

/*
 * Creates a simple run to complete job that computes π to 2000 places and prints it out.
 */
public class JobExample {
    private static final Logger logger = LoggerFactory.getLogger(JobExample.class);

    public static void main(String[] args) {

        try (final KubernetesClient client = new DefaultKubernetesClient(K8sUtil.getK8sConfig())) {
            final String namespace = "default";
            final Job job = new JobBuilder()
                    .withApiVersion("batch/v1")
                    .withNewMetadata()
                    .withName("pi")
                    .withLabels(Collections.singletonMap("label1", "maximum-length-of-63-characters"))
                    .withAnnotations(Collections.singletonMap("annotation1", "some-very-long-annotation"))
                    .endMetadata()
                    .withNewSpec()
                    .withNewTemplate()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("pi")
                    .withImage("perl")
                    .withArgs("perl", "-Mbignum=bpi", "-wle", "print bpi(2000)")
                    .endContainer()
                    .withRestartPolicy("Never")
                    .endSpec()
                    .endTemplate()
                    .endSpec()
                    .build();

            logger.info("Creating job pi.");
            client.batch().jobs().inNamespace(namespace).create(job);
            logger.info("Job pi is created, waiting for result...");

            final CountDownLatch watchLatch = new CountDownLatch(1);
            try (final Watch ignored = client.pods().inNamespace(namespace).withLabel("job-name").watch(new Watcher<Pod>() {
                @Override
                public void eventReceived(final Action action, Pod pod) {
                    if (pod.getStatus().getPhase().equals("Succeeded")) {
                        logger.info("Job pi is completed!");
                        logger.info(client.pods().inNamespace(namespace).withName(pod.getMetadata().getName()).getLog());
                        watchLatch.countDown();
                    }
                }

                @Override
                public void onClose(final KubernetesClientException e) {
                    logger.info("Cleaning up job pi.");
                    client.batch().jobs().inNamespace(namespace).delete(job);
                }
            })) {
                watchLatch.await(2, TimeUnit.MINUTES);
            } catch (final KubernetesClientException | InterruptedException e) {
                logger.error("Could not watch pod", e);
            }
        } catch (final KubernetesClientException e) {
            logger.error("Unable to create job", e);
        }
    }
}
