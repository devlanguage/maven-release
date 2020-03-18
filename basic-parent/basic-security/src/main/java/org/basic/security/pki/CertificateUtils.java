package org.basic.security.pki;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.basic.common.util.StreamUtils;
import org.basic.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.provider.X509Factory;

public class CertificateUtils {
    static final Logger logger = LoggerFactory.getLogger(CertificateUtils.class);

    public KeyPair getDefaultKeyPair() {
        logger.info("Into: getDefaultKeyPair()");

        String publ = "";
        String priv = "";

        try {
            if (StringUtils.hasText(publ) && StringUtils.hasText(priv)) {
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] privateKeyBytes = decoder.decode(priv);
                byte[] publicKeyBytes = decoder.decode(publ);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
                PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
                return new KeyPair(publicKey, privateKey);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.warn("KeyPair {PrivateKey and PublicKey} is invalid, will be regenerated. ", e);
        }

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            Base64.Encoder encoder = Base64.getEncoder();
            String privCode = encoder.encodeToString(privateKey.getEncoded());
            String publCode = encoder.encodeToString(publicKey.getEncoded());

            return keyPair;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            // this Exception should be unreachable
            logger.error("KeyPair generate fail, might be wrong jar. " + e.getMessage());
        }

        return null;
    }

    public X509Certificate generateCertificate(KeyPair keyPair) throws NoSuchAlgorithmException,
            CertificateEncodingException, NoSuchProviderException, InvalidKeyException, SignatureException {
        logger.info("Into: generateCertificate()");
        return generateCertificate(keyPair.getPublic(), keyPair.getPrivate());
    }

    /**
     * Delete public key with the given alias
     *
     * @param alias alias of the key to find
     * 
     */
    public void deleteCertificateFromKeystore(String alias, KeyStore keyStore) {
        logger.info("Into: deleteCertificate()");
        try {
            keyStore.deleteEntry(alias);
        } catch (KeyStoreException e) {
            logger.error("Error delete public certificate {}", alias);
        }
    }

    /**
     * Returns public key with the given alias
     *
     * @param alias alias of the key to find
     * @return public key of the alias or null if not found
     */
    public PublicKey getPublicKey(String alias, KeyStore keyStore) {
        logger.info("Into: getPublicKey()");
        X509Certificate x509Certificate = getCertificate(alias, keyStore);
        if (x509Certificate != null) {
            return x509Certificate.getPublicKey();
        } else {
            return null;
        }
    }

    /**
     * Returns certificate with the given alias from the keystore.
     *
     * @param alias alias of certificate to find
     * @return certificate with the given alias or null if not found
     */
    public X509Certificate getCertificate(String alias, KeyStore keyStore) {
        logger.info("Into: getCertificate()");
        if (alias == null || alias.length() == 0) {
            return null;
        }
        try {
            return (X509Certificate) keyStore.getCertificate(alias);
        } catch (Exception e) {
            logger.error("Error loading certificate", e);
        }
        return null;
    }

    public java.security.cert.X509Certificate pemToCertificate(String pem)
            throws CertificateException, UnsupportedEncodingException {
        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X509")
                .generateCertificate(new ByteArrayInputStream(pem.getBytes("UTF-8")));
        return cert;
    }

    public String certToPem(java.security.cert.X509Certificate cert) throws CertificateEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] derCert = cert.getEncoded();
        String pemCertPre = new String(encoder.encode(derCert));
        String pemCert = X509Factory.BEGIN_CERT + "\n" + pemCertPre + "\n" + X509Factory.END_CERT;
        return pemCert;
    }

    private static final int NOT_EXISTING_TYPE = -1;

    public String getUserIdentifier(X509Certificate clientCert) {
        if (logger.isDebugEnabled()) {
            logger.debug("getUserIdentifier(X509Certificate) - start");
        }
        int alternativeIdentifierTypeValue = 1; //
        String userIdentifier = null;
        if (clientCert != null) {
            if (alternativeIdentifierTypeValue != NOT_EXISTING_TYPE) {
                boolean foundUserIdentifier = false;
                try {
                    if (clientCert.getSubjectAlternativeNames() != null) {
                        Collection subjectAlternativeNames = clientCert.getSubjectAlternativeNames();
                        Iterator iter = subjectAlternativeNames.iterator();
                        while (iter.hasNext()) {
                            List subjectAlternativeName = (List) iter.next();
                            Integer type = (Integer) subjectAlternativeName.get(0);
                            if (type.intValue() == alternativeIdentifierTypeValue) {
                                Object subjectAlternativeNameValue = subjectAlternativeName.get(1);
                                if (subjectAlternativeNameValue instanceof String) {
                                    userIdentifier = (String) subjectAlternativeNameValue;
                                    foundUserIdentifier = true;
                                    break;
                                } else if (subjectAlternativeNameValue instanceof byte[]) {
                                    byte[] subjectAlternativeNameValueBytes = (byte[]) subjectAlternativeNameValue;
                                    userIdentifier = getStringFromASNDerEncodedByteArray(
                                            subjectAlternativeNameValueBytes);
                                    if (userIdentifier != null) {
                                        foundUserIdentifier = true;
                                        break;
                                    }
                                } else {
                                    if (logger.isInfoEnabled()) {
                                        logger.info(
                                                "VALIDATION - Cannot get UserIdentifier, the subjectAlternativeName not supported ["
                                                        + subjectAlternativeNameValue + "].");
                                    }
                                }
                            }
                        }
                    }
                } catch (CertificateParsingException e) {
                    logger.info(
                            "VALIDATION - Cannot get UserIdentifier, cannot get subjectAlternativeNames from certificate ["
                                    + e.getMessage() + "].",
                            e);
                }

            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("VALIDATION - Cannot get UserIdentifier, generalName is null");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("VALIDATION - Cannot get UserIdentifier, clientCert is null");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("getUserIdentifier(X509Certificate) - end; Ret is [" + userIdentifier + "].");
        }

        return userIdentifier;
    }

    private String getStringFromASNDerEncodedByteArray(byte[] byteArray) {
        if (logger.isDebugEnabled()) {
            logger.debug("getStringFromASNDerEncodedByteArray(byte[]) - start");
        }

        String ret = null;
//		ASN1InputStream asn1InputStream = null;
//		try {
//			asn1InputStream = new ASN1InputStream(new ByteArrayInputStream(byteArray));
//			ASN1Primitive derObject = asn1InputStream.readObject();
//			ASN1Sequence asn1Sequence = ASN1Sequence.getInstance(derObject);
//			Object objectValue = asn1Sequence.getObjectAt(1);
//			if (objectValue instanceof ASN1TaggedObject) {
//				ASN1TaggedObject asn1TaggedObject = (ASN1TaggedObject) objectValue;
//				try {
//					if (logger.isDebugEnabled()) {
//						logger.debug("Try to get string from DERUTF8String.");
//					}
//					ASN1Primitive derTaggedObject = asn1TaggedObject.getObject();
//					DERUTF8String derUtf8String = DERUTF8String.getInstance(derTaggedObject);
//					ret = derUtf8String.getString();
//				} catch (IllegalArgumentException e) {
//					if (logger.isDebugEnabled()) {
//						logger.debug("VALIDATION - Can ot get String From DERUTF8String, [" + e.getMessage() + "].", e);
//					}
//				}
//			}
//		} catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				logger.info("VALIDATION - Cannot get String From ASNDerEncoded ByteArray, [" + e.getMessage() + "].");
//			}
//		} finally {
//			StreamUtils.closeQuietly(asn1InputStream);
//		}
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("VALIDATION - getStringFromASNDerEncodedByteArray(byte[]) - end. Ret is [" + ret + "].");
//		}
        return ret;

    }

    public X509Certificate generateCertificate(PublicKey publicKey, PrivateKey privateKey)
            throws NoSuchAlgorithmException, CertificateEncodingException, NoSuchProviderException, InvalidKeyException,
            SignatureException {

        logger.info("Into: generateCertificate(,)");
        String certCode = "";
        if (StringUtils.hasText(certCode)) {
            try {
                return pemToCertificate(certCode);
            } catch (Exception e) {
                logger.warn("Failed to get certificate from DB: ", e.getMessage());
                logger.debug("Detailed exception: ", e);
            }
        }

        X509Certificate certificate = null;
//		
//		int days = 365;
//		long secOfDay = 86400000L;
//		org.bouncycastle.x509.X509V3CertificateGenerator cert = new org.bouncycastle.x509.X509V3CertificateGenerator();
//		cert.setSerialNumber(BigInteger.valueOf(1)); // or generate a random number
//		cert.setSubjectDN(new org.bouncycastle.jce.X509Principal("CN=localhost")); // see examples to add O,OU etc
//		cert.setIssuerDN(new org.bouncycastle.jce.X509Principal("CN=localhost")); // same since it is self-signed
//		cert.setPublicKey(publicKey);
//		Date now = new Date();
//		cert.setNotBefore(new Date(now.getTime() - secOfDay));
//		cert.setNotAfter(new Date(now.getTime() + days * secOfDay));
//		cert.setSignatureAlgorithm("SHA256WithRSA");// "SHA1WithRSAEncryption");
//		PrivateKey signingKey = privateKey;
//		X509Certificate certificate = cert.generate(signingKey);

        return certificate;
    }

    public KeyStore initialize(File storeFile, String storePass, String storeType) throws KeyStoreException {
        logger.info("Into: initialize()");
        InputStream inputStream = null;
        KeyStore ks;
        String provider = "";// BCFIPS,SUNJCE
        try {
            ks = StringUtils.hasText(provider) ? KeyStore.getInstance(storeType, provider)
                    : KeyStore.getInstance(storeType);
            inputStream = new FileInputStream(storeFile);
            ks.load(inputStream, storePass == null ? null : storePass.toCharArray());
            return ks;
        } catch (KeyStoreException e) {
            logger.warn("KeyStoreException:" + e.getMessage());
            logger.debug("Cannot initialize key store with keystoreFile=" + storeFile, e);
        } catch (IOException e) {
            logger.warn("IOException:" + e.getMessage());
            logger.debug("Cannot initialize key store with keystoreFile=" + storeFile, e);
        } catch (Exception e) {
            logger.warn("Exception:" + e.getMessage());
            logger.debug("Cannot initialize key store with keystoreFile=" + storeFile, e);
        } finally {
            StreamUtils.closeQuietly(inputStream);
        }
        return generateDefaultKeystore(DEFAULT_KEYSTORE_PASSWORD, DEFAULT_KEYSTORE_DEFAULT_KEY,
                DEFAULT_KEYSTORE_DEFAULT_PASSWORD);
    }

    private String DEFAULT_KEYSTORE_PASSWORD = "testKeystore.jks";
    private String DEFAULT_KEYSTORE_DEFAULT_KEY = "testKeyAlias1";
    private String DEFAULT_KEYSTORE_DEFAULT_PASSWORD = "testKeyAlias1_Pass";

    public KeyStore generateDefaultKeystore(String keyStorePass, String keyAlias, String keyPassword)
            throws KeyStoreException {
        logger.warn("Warning: Default Keystore In Use.");
        logger.info("Generating Default Keystore.");
        KeyStore keyStore = KeyStore.getInstance("JKS");

        try {
            KeyPair keyPair = getDefaultKeyPair();
            X509Certificate certificate = generateCertificate(keyPair);
            keyStore.load(null, null);
            Certificate[] certChain = new Certificate[1];
            certChain[0] = certificate;

            keyStore.setKeyEntry(keyAlias, keyPair.getPrivate(), keyPassword.toCharArray(), certChain);

            // keyStore.store(FileOutputStream("newKeystore.jks", keyStorePass);
        } catch (Exception e) {
            logger.error("Automatically generate key store failed: " + e.getMessage());
        }
        return keyStore;
    }

    // KeyStore newKeyStore = initialize(keystoreFile, passwordValue, typeValue);
    // Map<String, String> passwords = new
    // HashMap<>(1);passwords.put(this.defaultKey,this.password);

}
