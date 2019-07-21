package org.third.fabric8.kubernetes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;

public class WatchExample {

  private static final Logger logger = LoggerFactory.getLogger(WatchExample.class);

  public static void main(String[] args) throws InterruptedException {
    final CountDownLatch closeLatch = new CountDownLatch(1);
    Config config = new ConfigBuilder().build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      try (Watch watch = client.replicationControllers().inNamespace("default").withName("test").watch(new Watcher<ReplicationController>() {
        @Override
        public void eventReceived(Action action, ReplicationController resource) {
          logger.info("{}: {}", action, resource.getMetadata().getResourceVersion());
        }

        @Override
        public void onClose(KubernetesClientException e) {
          logger.debug("Watcher onClose");
          if (e != null) {
            logger.error(e.getMessage(), e);
            closeLatch.countDown();
          }
        }
      })) {
        closeLatch.await(10, TimeUnit.SECONDS);
      } catch (KubernetesClientException | InterruptedException e) {
        logger.error("Could not watch resources", e);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage(), e);

      Throwable[] suppressed = e.getSuppressed();
      if (suppressed != null) {
        for (Throwable t : suppressed) {
          logger.error(t.getMessage(), t);
        }
      }
    }
    Thread.sleep(60000l);
  }

}
