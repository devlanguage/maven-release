package org.basic.security.pki;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.pkcs13.PKCS12KeyStore;

public class KeyStoreUtil {
	static final Logger logger = LoggerFactory.getLogger(KeyStoreUtil.class);

	@Test
	public void testPKCS12() throws Exception {
//		KeyStore ks = KeyStore.getInstance("PKCS12");
//		ks.load(KeyStoreUtil.class.getResourceAsStream("dynasecurw1.hpeswlab.net.p12"), "changeit".toCharArray());
		PKCS12KeyStore ks = new PKCS12KeyStore();
		ks.engineLoad(KeyStoreUtil.class.getResourceAsStream("dynasecurw1.hpeswlab.net.p12"), "changeit".toCharArray());
	}

	@Test
	public void testPkcs12Ase128() throws Exception {
//		KeyStore ks = PKCS12KeyStore.engineLoad("PKCS12");
		PKCS12KeyStore ks = new PKCS12KeyStore();
		ks.engineLoad(KeyStoreUtil.class.getResourceAsStream("tomcat_aes128.p12"), "tomcat".toCharArray());

	}

	public static void main(String[] args) {
		PKCS12KeyStore ks = new PKCS12KeyStore();
		try {
			ks.engineLoad(KeyStoreUtil.class.getResourceAsStream("dynasecurw1.hpeswlab.net.p12"), "changeit".toCharArray());

			ks.engineLoad(KeyStoreUtil.class.getResourceAsStream("tomcat_3des.p12"), "tomcat".toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
