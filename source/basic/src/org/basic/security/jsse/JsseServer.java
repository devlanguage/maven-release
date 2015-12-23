/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 10:58:39 AM Mar 6, 2014
 *
 *****************************************************************************
 */
package org.basic.security.jsse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
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
 *  采用同样的方法，生成客户端的私钥，客户端的证书，并且导入到服务端的Trust KeyStore中
 *   1) keytool -genkey -keyalg RSA -keystore kclient.keystore -storetype jks -storepass tellabs1$ -keysize 1024 -validity 1460 -alias client_key -keypass client_key_pwd -dname "CN=Client, OU=MSO, C=US"
 *   2) keytool -export -keystore kclient.keystore -storetype jks -storepass tellabs1$ -alias client_key -file client_key.crt
 *   3) keytool -import -noprompt -keystore tserver.keystore -storetype jks -storepass tellabs1$ -alias client_key -file client_key.crt
 * 
 * 
 * 如此，就完成了服务端和客户端之间的基于身份认证的交互。
 * client采用kclient.keystore中的clientkey私钥进行数据加密，发送给server。
 * server采用tserver.keystore中的client.crt证书（包含了clientkey的公钥）对数据解密，如果解密成功，证明消息来自client，进行逻辑处理。
 * 
 * server采用kserver.keystore中的serverkey私钥进行数据加密，发送给client。
 * client采用tclient.keystore中的server.crt证书（包含了serverkey的公钥）对数据解密，如果解密成功，证明消息来自server，进行逻辑处理。
 * 如果过程中，解密失败，那么证明消息来源错误。不进行逻辑处理。这样就完成了双向的身份认证。
 * </pre>
 * 
 * <pre>
 * $ keytool -genkey -keystore kserver.keystore -storepass tellabs1$ -alias server_key2 -keypass server_key2_pwd -dname "CN=sariel.javaeye.com, OU=sariel CA, O=sariel Inc, L=Stockholm, S=Stockholm, C=SE"
 * </pre>
 */
public class JsseServer implements Runnable {

    private static final int DEFAULT_PORT = 4343;

    private SSLServerSocket serverSocket;

    /**
     * 启动程序
     * 
     * @param args
     */
    public static void main(String[] args) {
        JsseServer server = new JsseServer();
        server.init();
        Thread thread = new Thread(server);
        thread.start();
    }

    public synchronized void start() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);

                byte[] buffer = new byte[20];
                bis.read(buffer);
                System.out.println("------receive:--------" + new String(buffer).toString());

                bos.write("received:\nInput>".getBytes());
                bos.flush();

                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static final String KEY_STORE_DIR = "D:/Cloud/Prj_files/JavaBasic/java/basic/src/org/basic/security/jsse/";
    public static final String SERVER_KEY_STORE = KEY_STORE_DIR + "kserver.keystore";
    public static final String SERVER_TRUSTED_KEY_STORE = KEY_STORE_DIR + "tserver.keystore";
    public static final String CLIENT_KEY_STORE = KEY_STORE_DIR + "kclient.keystore";
    public static final String CLIENT_TRUSTED_KEY_STORE = KEY_STORE_DIR + "tclient.keystore";

    private static final String SERVER_KEY_STORE_PWD = "tellabs1$";
    private static final String SERVER_TRUSTED_KEY_STORE_PWD = "tellabs1$";

    public void init() {
        try {

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("jks"); // 实例化密钥库
            KeyStore tks = KeyStore.getInstance("jks"); // 实例化密钥库
            // 获得密钥库文件流 // 加载密钥库
            ks.load(new FileInputStream(SERVER_KEY_STORE), SERVER_KEY_STORE_PWD.toCharArray());
            tks.load(new FileInputStream(SERVER_TRUSTED_KEY_STORE), SERVER_TRUSTED_KEY_STORE_PWD.toCharArray());

            kmf.init(ks, "server_key_pwd".toCharArray());
//            kmf.init(ks, "server_key_pwd2".toCharArray());

            tmf.init(tks);

            // System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
            SSLContext ctx = SSLContext.getInstance("SSL");// SSLContext context = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            // ctx.init(kmf.getKeyManagers(), null, null);

            javax.net.ssl.SSLServerSocketFactory sslSf = ctx.getServerSocketFactory();
            serverSocket = (SSLServerSocket) sslSf.createServerSocket(DEFAULT_PORT);
            serverSocket.setNeedClientAuth(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void run() {
        // TODO Auto-generated method stub
        start();
    }
}