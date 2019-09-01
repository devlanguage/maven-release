package org.third.fabric8.kubernetes;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;

public class K8sUtil {
	public static final String master = "http://h9.test1.com:8001";

	public static final String CA_CRT="-----BEGIN CERTIFICATE-----\r\n" + 
			"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\r\n" + 
			"rj2TxSPaCa4CzSUMRB3wx0GUQm1ffWqPP1E/\r\n" + 
			"-----END CERTIFICATE-----";
	public static final String SERVER_CRT="-----BEGIN CERTIFICATE-----\r\n" + 
			"MIIFdjCCA16gAwIBAgIJAPu87pLtMt76MA0GCSqGSIb3DQEBCwUAMCoxKDAmBgNV\r\n" + 
			"BAMMH01GIENERiBSSUMgQ0Egb24gaDkuZmVpdGlhbi5jb20wHhcNMTkwODAyMDIy\r\n" + 
			"NjE5WhcNMjAwODAxMDIyNjE5WjAZMRcwFQYDVQQDDA5oOS5mZWl0aWFuLmNvbTCC\r\n" + 
			"AiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAP69N9XZQtp2BBeLXTVNUS6g\r\n" + 
			"aPqyABxDByBz3oSdvG19NL6YzFICIp1KgJwgjdfiaCbj8DPgDDy6gZat3luk2sOU\r\n" + 
			"hTAkP0Dkw+s+2ZcSSvfEdl9QBQcMZ37uGdJSxxRLnqMkRdunC+L47JJSBdwwOpOk\r\n" + 
			"wCutsSB6a+ewjvW23f7DljOn9EXPGQT8ROZqcv0AocZu3XImkqG0r1Orl26OXI1a\r\n" + 
			"tUnaNiJNsP7wJC8M1Qb1d8QJre0J0TV9oyd5Fldq4fRsbGXxfCSkho7nQeVA5bjK\r\n" + 
			"uVK1rARD8FzGZTLDVyb5UqPi4fOEBrDQs99ncu6tVvm5dbLB6Vv6iPTw9hlEqSk4\r\n" + 
			"m8iJnuoIVx2MSlCQgBtYIfyi6axETXa9QUf7kkTX2bd4JoR4u8ADgP1Q3Qd+KMb/\r\n" + 
			"zZzKExWMDbE+JqkSpriP+95k29Oz7IyDbmazHn+2nv6NjpiGn60oIvPg6vkpBeyQ\r\n" + 
			"fJ6Kfa51HOvAXW3rs3BFsp+xg6W1FC/RfokJ98VlUGzbXoLCqtkVOyyGRpgKEqUm\r\n" + 
			"TPYd3YGVh0X88kRPMINs8QQI5A4s4nj2MpdqRTcV2rY0k+1THAO3iMklRaBZC5CA\r\n" + 
			"VRr6a+uwUyAHX8z3yoUChXBbxUJvejhbT4PUc7x+9hsrDubr9p34j7gQMIJGmcQ7\r\n" + 
			"ZBHPljgoGWO0CvZIQEopAgMBAAGjga8wgawwgakGA1UdEQSBoTCBnocErBERAYIK\r\n" + 
			"a3ViZXJuZXRlc4ISa3ViZXJuZXRlcy5kZWZhdWx0ghZrdWJlcm5ldGVzLmRlZmF1\r\n" + 
			"bHQuc3ZjgiRrdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWyCDmg5\r\n" + 
			"LmZlaXRpYW4uY29tgg5oOS5mZWl0aWFuLmNvbYcEwKgAbYIOaDkuZmVpdGlhbi5j\r\n" + 
			"b22CAmg5MA0GCSqGSIb3DQEBCwUAA4ICAQAWOX0F1+FwVFG+7GDyBj6MoX3PqB0+\r\n" + 
			"Vw7gV13yEVnvbiXnODh2zN35F8d976CYVLuTsuTLE47kb41ggodDnUOUqycYVm+n\r\n" + 
			"KsT211LNftX362c1y/I1/IkrGz7CbQSuNJNmrQReChUA7Z4RNqs9gbKMj+duqiZ6\r\n" + 
			"ESF4AyYQu7VZ3aWSVmlEN17NBYYx7R7rxSuIRiU0Bc8ba72yF2tn/aDyRpUq4qPE\r\n" + 
			"Izhxqb7Aoi78a5BzrRVEIpTbSEzZWhaHqsPMB5hWmBliHKUMy4lW1TSrxsNjC0o2\r\n" + 
			"Rzx9Yxqua4ng2rMCV9r2Ap1chEPPf5D1OHhidJD+wOiPniK0cNmXXa6SBHCCkdqN\r\n" + 
			"FK32a/8kZZ0Jz2Fknyo3y4gK4dkdodLLBJcV9NdjWUV5MTFXByzRhyQJTFgwJUFP\r\n" + 
			"wZI3LLzFjL4Fnts2iolwo7FJPfrbOqAvjsWugr47+ybJWSxH6LIBZCZXZ2uIRciJ\r\n" + 
			"AWwZC6iFTTFna3grz1dexRZgbBAT/ZHPBoJHRz/26IfHQjAcAz9ne3acuWgkmWnn\r\n" + 
			"6YzRVTay06K5P+CSvBnnSWk7AmcvpLyu/5hkgFgyDVOl78sE2yttjdKTKsxg/TF5\r\n" + 
			"zz6RPHV9Z6pRQECV4RmpiHogY31xV4eoIVFTPIifLf+fQtWjjMG78qMD/BD+MuFh\r\n" + 
			"NLMPEiKX8m4Q5w==\r\n" + 
			"-----END CERTIFICATE-----";
	public static final String SERVER_KEY="-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"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\r\n" + 
			"-----END RSA PRIVATE KEY-----"; 

	public static Config getK8sConfig() {
		return getK8sConfig(master);
	}

	public static Config getK8sConfig(String master) {
		return getK8sConfigBuilder(master).build();
	}

	public static ConfigBuilder getK8sConfigBuilder() {
		return getK8sConfigBuilder(master);
	}

	public static ConfigBuilder getK8sConfigBuilder(String master) {
		ConfigBuilder config = new ConfigBuilder().withMasterUrl(master)
//				.withCaCertData(CA_CRT)
//				.withClientCertData(SERVER_CRT)
//				.withClientKeyData(SERVER_KEY)
				;
		return config;
	}
}
