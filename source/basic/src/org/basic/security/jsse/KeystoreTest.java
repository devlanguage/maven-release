
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

public class KeystoreTest {
    static final String KEYSTORE_FILE = "D:/Cloud/Prj_files/JavaBasic/java/basic/src/org/basic/security/jsse/kserver.keystore";
    static final String CERTFICATE_FILE = "D:/Cloud/Prj_files/JavaBasic/java/basic/src/org/basic/security/jsse/server_key.crt";

    static final void printCertification(Certificate c) {
        if (c instanceof java.security.cert.X509Certificate) {
            java.security.cert.X509Certificate t = (java.security.cert.X509Certificate) c;
            System.out.println("版本号:" + t.getVersion());
            System.out.println("序列号:" + t.getSerialNumber().toString(16));
            System.out.println("主体名：" + t.getSubjectDN());
            System.out.println("签发者：" + t.getIssuerDN());
            System.out.println("有效期：" + t.getNotBefore());
            System.out.println("签名算法：" + t.getSigAlgName());
            byte[] sig = t.getSignature();// 签名值
        }
        PublicKey pk = c.getPublicKey();
        byte[] pkenc = pk.getEncoded();
        System.out.println("公钥");
        for (int i = 0; i < pkenc.length; i++) {
            System.out.print(pkenc[i] + ",");
        }
        System.out.println("输出证书信息:\n" + c.toString());
    }

    static final void readCertificateFromFile(String crtFile) throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(crtFile);
        Certificate c = cf.generateCertificate(in);
        printCertification(c);
    }

    static final void readCertificateFromKeystore(String keystoreFile) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        String pass = "tellabs1$", alias = "server_key";
        FileInputStream in = new FileInputStream(keystoreFile);
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(in, pass.toCharArray());

        // java.security.cert.Certificate c = ks.getCertificate(alias);// alias为条目的别名
        Enumeration<String> e = ks.aliases();
        while (e.hasMoreElements()) {
            alias = e.nextElement();
            java.security.cert.Certificate c = ks.getCertificate(alias);// alias为条目的别名
            System.out.println("*********************************");
            System.out.println("别名:" + alias);
            printCertification(c);

            System.out.println("**************************************");
        }

    }

    public static void main(String[] args) {
        try {
            readCertificateFromFile(KEYSTORE_FILE);
            readCertificateFromKeystore(KEYSTORE_FILE);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }
}