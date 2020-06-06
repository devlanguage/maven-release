package org.third.monitor.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;

public class PrometheusMetrics {
    public static void main(String[] args) {
        // core_subsystem_idm_user_active
        Counter counterCollector = Counter.build().name("idm_user_active_total").help("number of active session")
                .labelNames("host").namespace("core").subsystem("subsystem").register();
        System.out.println(counterCollector.describe());

        Gauge guageCollector = Gauge.build().name("idm_user_active").help("number of active session").labelNames("host")
                .namespace("core").subsystem("subsystem").register();
        System.out.println(guageCollector.describe());

        Summary summaryCollector = Summary.build().name("idm_user_active_summary").help("number of active session").labelNames("host")
                .namespace("core").subsystem("subsystem").ageBuckets(21).maxAgeSeconds(60).quantile(0.1, 0.8)
                .register();
        System.out.println(summaryCollector.describe());

        Histogram histogramCollector = Histogram.build().name("idm_user_active_histogram").help("number of active session")
                .labelNames("host").namespace("core").subsystem("subsystem").buckets(12.1, 13.1)
                .exponentialBuckets(0.11, 0.70, 10).register();
        System.out.println(histogramCollector.describe());

    }

}
