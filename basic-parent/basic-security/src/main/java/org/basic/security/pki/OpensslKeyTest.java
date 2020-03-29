package org.basic.security.pki;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.basic.common.util.CodecUtils;
import org.basic.common.util.StreamUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.junit.Test;

public class OpensslKeyTest {

    @Test
    public void testParseUncryptedPkcs1() throws Exception {
        InputStream pk8TextInput = null;
        pk8TextInput = OpensslKeyTest.class.getResourceAsStream("openssl/rsa");
        String pk8Text = StreamUtils.streamToString(pk8TextInput);
        String opensslRsaKey = pk8Text.replaceAll("-----BEGIN (.*) KEY-----", "").replaceAll("-----END (.*) KEY-----",
                "");
        RSAPrivateKey asn1PrivKey = RSAPrivateKey
                .getInstance((ASN1Sequence) ASN1Sequence.fromByteArray(CodecUtils.decodeBase64(opensslRsaKey, true)));
        RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(),
                asn1PrivKey.getPrivateExponent());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivKeySpec);

        System.out.println(CodecUtils.encodeBase64String(privateKey.getEncoded()));
    }

    @Test
    public void testParseUncryptedPkcs8() throws Exception {
        InputStream pk8TextInput = null;
        pk8TextInput = OpensslKeyTest.class.getResourceAsStream("openssl/rsa.pk8");
        String pk8Text = StreamUtils.streamToString(pk8TextInput);
        String opensslRsaKey = pk8Text.replaceAll("-----BEGIN (.*) KEY-----", "").replaceAll("-----END (.*) KEY-----",
                "");

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(CodecUtils.decodeBase64(opensslRsaKey, true));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        java.security.PrivateKey privateKey = kf.generatePrivate(spec);

        System.out.println(CodecUtils.encodeBase64String(privateKey.getEncoded()));
    }

    @Test
    public void testParseEncryptedPkcs8() throws Exception {
        InputStream pk8TextInput = null;
        pk8TextInput = OpensslKeyTest.class.getResourceAsStream("openssl/rsa.pk8.crypt");
        String pk8Text = StreamUtils.streamToString(pk8TextInput);
        String opensslRsaKey = pk8Text.replaceAll("-----BEGIN (.*) KEY-----", "").replaceAll("-----END (.*) KEY-----",
                "");

        EncryptedPrivateKeyInfo pkInfo = new EncryptedPrivateKeyInfo(CodecUtils.decodeBase64(opensslRsaKey, true));
        PBEKeySpec keySpec = new PBEKeySpec("test1".toCharArray()); // password
        SecretKeyFactory pbeKeyFactory = SecretKeyFactory.getInstance(pkInfo.getAlgName());
        PKCS8EncodedKeySpec encodedKeySpec = pkInfo.getKeySpec(pbeKeyFactory.generateSecret(keySpec));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey encryptedPrivateKey = keyFactory.generatePrivate(encodedKeySpec);

        System.out.println(CodecUtils.encodeBase64String(encryptedPrivateKey.getEncoded()));

    }

    public static void main(String[] args) {

    }
}
