package org.third.monitor.prometheus;

import io.prometheus.client.Gauge;

//Gauges can go up and down.
public class PrometheusSummary {
    static final Gauge inprogressRequests = Gauge.build().name("inprogress_requests").help("Inprogress requests.")
            .register();

    void processRequest() {
        inprogressRequests.inc();
        // Your code here.
        inprogressRequests.dec();
        
//        gauge.setToCurrentTime(); // Set to current unixtime.

    }
}