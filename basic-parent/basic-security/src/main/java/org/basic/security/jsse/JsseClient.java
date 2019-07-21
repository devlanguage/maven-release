package org.basic.security.jsse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * <pre>
 * JSSE（Java Security Socket Extension）
 * 是Sun为了解决在Internet上的实现安全信息传输的解决方案。它实现了SSL和TSL（传输层安全）协议。在JSSE中包含了数据加密，服务器验证，消息完整性和客户端验证等技术。通过使用JSSE，可以在Client和Server之间通过TCP/IP协议安全地传输数据。
 * 为了实现消息认证。
 * Server需要：
 * 1）KeyStore: 其中保存服务端的私钥
 * 2）Trust KeyStore:其中保存客户端的授权证书
 * Client需要：
 * 1）KeyStore：其中保存客户端的私钥
 * 2）Trust KeyStore：其中保存服务端的授权证书
 * 
 * 使用Java自带的keytool命令，去生成这样信息文件：
 *   1）生成服务端私钥，并且导入到服务端KeyStore文件中
 *      keytool -genkey -keyalg RSA -keystore kserver.keystore -storetype jks -storepass tellabs1$ -keysize 1024 -validity 1460 -alias server_key -keypass server_key_pwd -dname "CN=Client, OU=MSO, C=US"
 *   2）根据私钥，导出服务端证书
 *      keytool -export -keystore kserver.keystore -storetype jks -storepass tellabs1$ -alias server_key -file server_key.crt
 *   3）将服务端证书，导入到客户端的Trust KeyStore中
 *      keytool -import -noprompt -keystore tclient.keystore -storetype jks -storepass tellabs1$ -alias server_key -file server_key.crt
 *      
 *       采用同样的方法，生成客户端的私钥，客户端的证书，并且导入到服务端的Trust KeyStore中
 * 1）keytool -genkey -keyalg RSA -keystore kclient.keystore -storetype jks -storepass tellabs1$ -keysize 1024 -validity 1460 -alias client_key -keypass client_key_pwd -dname "CN=Client, OU=MSO, C=US"
 * 2）keytool -export -keystore kclient.keystore -storetype jks -storepass tellabs1$ -alias client_key -file client_key.crt
 * 3） keytool -import -noprompt -keystore tserver.keystore -storetype jks -storepass tellabs1$ -alias client_key -file client_key.crt
 * 
 * 如此，就完成了服务端和客户端之间的基于身份认证的交互。
 * client采用kclient.keystore中的clientkey私钥进行数据加密，发送给server。
 * server采用tserver.keystore中的client.crt证书（包含了clientkey的公钥）对数据解密，如果解密成功，证明消息来自client，进行逻辑处理。
 * 
 * server采用kserver.keystore中的serverkey私钥进行数据加密，发送给client。
 * client采用tclient.keystore中的server.crt证书（包含了serverkey的公钥）对数据解密，如果解密成功，证明消息来自server，进行逻辑处理。
 * 如果过程中，解密失败，那么证明消息来源错误。不进行逻
 * 
 * </pre>
 */
public class JsseClient {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7777;


    private SSLSocket sslSocket;

    /**
     * 启动客户端程序
     * 
     * @param args
     */
    public static void main(String[] args) {
        JsseClient client = new JsseClient();
        client.init();
        client.process();
    }

    public void process() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);

            Scanner s = new Scanner(System.in);
//            bos.write("1234567890".getBytes());
            System.out.print("input>");
            bos.write(s.nextLine().getBytes());
            bos.flush();

            byte[] buffer = new byte[20];
            bis.read(buffer);
            System.out.println(new String(buffer));

            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static final String KEY_STORE_DIR = "D:/Cloud/Prj_files/JavaBasic/java/basic/src/org/basic/security/jsse/";
    public static final String SERVER_KEY_STORE = KEY_STORE_DIR + "kserver.keystore";
    public static final String SERVER_TRUSTED_KEY_STORE = KEY_STORE_DIR + "tserver.keystore";
    public static final String CLIENT_KEY_STORE = KEY_STORE_DIR + "kclient.keystore";
    public static final String CLIENT_TRUSTED_KEY_STORE = KEY_STORE_DIR + "tclient.keystore";

    private static final String CLIENT_KEY_STORE_PASSWORD = "tellabs1$";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "tellabs1$";
    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream(CLIENT_KEY_STORE), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream(CLIENT_TRUSTED_KEY_STORE), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, "client_key_pwd".toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
