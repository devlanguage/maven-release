package org.third.monitor.prometheus;

import io.prometheus.client.Counter;
//Counters go up, and reset when the process restarts.

public class PrometheusCounter {
    static final Counter requests = Counter.build().name("requests_total").help("Total requests.").register();

    void processRequest() {
        requests.inc();
        // Your code here.
    }
}