package org.basic.jdk.jdk7.e5_networking.httpserver;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

public class HttpServerTest {

    static final int MAX_CONCURRENT_REQUESTS = 100;

    /**
     * <pre>
     * 实现HTTPS SERVER//
     * 
     * Server需要：
     *  1）KeyStore: 其中保存服务端的私钥
     *  2）Trust KeyStore:其中保存客户端的授权证书
     * Client需要：
     *  1）KeyStore：其中保存客户端的私钥
     *  2）Trust KeyStore：其中保存服务端的授权证书
     * 
     * keytool -genkey -keyalg RSA -keystore client.jks -storetype jks -storepass tellabs1$
     *                  -alias client_rsa -keypass password -dname "CN=Client, OU=MSO, C=US" -keysize 1024 -validity 1460
     * keytool -genkey -keyalg RSA -keystore server.jks -storetype jks -storepass tellabs1$ -alias server_rsa
     * 
     * keytool -export -keystore client.jks -storetype jks -storepass tellabs1$ -alias client_rsa -file
     * keytool -export -keystore server.jks -storetype jks -storepass tellabs1$  -alias server_rsa -file server_rsa.cer
     * 
     * keytool -import -noprompt -keystore server.jks -storetype jks -storepass tellabs1$  -alias client_rsa -file client_rsa.cer
     * keytool -import -noprompt -keystore client.jks -storetype jks -storepass tellabs1$  -alias server_rsa -file server_rsa.cer
     * </pre>
     * 
     * @param args
     */
    public static void main(String[] args) {

        try {

            HttpServer hs = HttpServer.create(new InetSocketAddress(8080), MAX_CONCURRENT_REQUESTS);// 设置HttpServer的端口为8888
            hs.createContext("/test", new HttpRequestHandler());// 用MyHandler类内处理到/chinajash的请求
            hs.setExecutor(null); // creates a default executor
            hs.start();
            System.out.println("http server started in 8080");

            // client_rsa.cer
            char[] keystorePwd = "tellabs1$".toCharArray();
            HttpsServer hss = HttpsServer.create(new InetSocketAddress(4343), MAX_CONCURRENT_REQUESTS); // 设置HTTPS端口这4343
            KeyStore ks = KeyStore.getInstance("JKS"); // 建立证书库
            ks.load(HttpServerTest.class.getResourceAsStream("server.jks"), keystorePwd); // 载入证书
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509"); // 建立一个密钥管理工厂
            kmf.init(ks, "password".toCharArray()); // 初始工厂
            SSLContext sslContext = SSLContext.getInstance("SSLv3"); // 建立证书实体
            sslContext.init(kmf.getKeyManagers(), null, null); // 初始化证书
            HttpsConfigurator conf = new HttpsConfigurator(sslContext); // 在https配置
            hss.setHttpsConfigurator(conf); // 在https server载入配置
            hss.setExecutor(null); // creates a default executor
            hss.createContext("/test", new HttpRequestHandler());// 用MyHandler类内处理到/的请求
            hss.start();
            System.out.println("https server started in 4343");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
