package org.third.monitor.prometheus;

import org.bouncycastle.asn1.ocsp.Request;

import io.prometheus.client.Summary;

//Gauges can go up and down.
public class PrometheusGauge {
    static final Summary receivedBytes = Summary.build().name("requests_size_bytes").help("Request size in bytes.")
            .register();
    static final Summary requestLatency = Summary.build().name("requests_latency_seconds")
            .help("Request latency in seconds.").register();

    // cdf_ric_certificate_external_metrics{dns_names="shcemersonvm01.hpeswlab.net",email_addresses="",hostname="shcemersonvm01.hpeswlab.net",issuer="CN=MF
    // CDF RE CA on
    // shcemersonvm01.hpeswlab.net",status="",subject="CN=shcemersonvm01.hpeswlab.net",version="3"}
    // 3.1099522041769553e+07
    void processRequest(Request req) {
        Summary.Timer requestTimer = requestLatency.startTimer();
        try {
            // Your code here.
        } finally {
//            receivedBytes.observe(req.size());
            requestTimer.observeDuration();
        }
    }

    public static void main(String[] args) {
        System.out.println(receivedBytes.describe());
    }
}