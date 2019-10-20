
package org.third.netty.ocsp;

import java.io.OutputStream;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.bouncycastle.operator.DigestCalculator;

/**
 * BC's {@link OCSPReqBuilder} needs a {@link DigestCalculator} but BC doesn't
 * provide any public implementations of that interface. That's why we need to
 * write our own. There's a default SHA-1 implementation and one for SHA-256.
 * Which one to use will depend on the Certificate Authority (CA).
 */
public final class Digester implements DigestCalculator {

    public static DigestCalculator sha1() {
        Digest digest = new SHA1Digest();
        AlgorithmIdentifier algId = new AlgorithmIdentifier(
                OIWObjectIdentifiers.idSHA1);

        return new Digester(digest, algId);
    }

    public static DigestCalculator sha256() {
        Digest digest = new SHA256Digest();

        // The OID for SHA-256: http://www.oid-info.com/get/2.16.840.1.101.3.4.2.1
        ASN1ObjectIdentifier oid = new ASN1ObjectIdentifier(
                "2.16.840.1.101.3.4.2.1").intern();
        AlgorithmIdentifier algId = new AlgorithmIdentifier(oid);

        return new Digester(digest, algId);
    }

    private final DigestOutputStream dos;

    private final AlgorithmIdentifier algId;

    private Digester(Digest digest, AlgorithmIdentifier algId) {
        this.dos = new DigestOutputStream(digest);
        this.algId = algId;
    }

    @Override
    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return algId;
    }

    @Override
    public OutputStream getOutputStream() {
        return dos;
    }

    @Override
    public byte[] getDigest() {
        return dos.getDigest();
    }
}
