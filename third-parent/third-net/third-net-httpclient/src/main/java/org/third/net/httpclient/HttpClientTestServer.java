package org.third.net.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.basic.common.util.StreamUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

/**
 * Created on Mar 6, 2014, 10:12:10 AM
 */
public class HttpClientTestServer {
	public static final int HTTP_SERVER_PORT = 8080;
	public static final int HTTPS_SERVER_PORT = 4343;

	public static String HTTP_SERVER_NAME = "";

	private static InetAddress localhost;
	static {
		try {
			localhost = InetAddress.getLocalHost();
			HTTP_SERVER_NAME = localhost.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(HTTP_SERVER_PORT), 12);
			httpServer.createContext("/test", new HttpHandler() {
				@Override
				public void handle(HttpExchange t) throws IOException {
					Headers reqHeader = t.getRequestHeaders();
					for (Object e : reqHeader.entrySet()) {
						System.out.println(e);
					}

					InputStream reqBody = t.getRequestBody();
					System.out.println(StreamUtils.toString(reqBody));
					String response = "<h3>Happy New Year 2007! -- Chinajash</h3>";
					t.sendResponseHeaders(200, response.length());
					OutputStream os = t.getResponseBody();
					os.write(response.getBytes());
					os.close();
				}
			});
			httpServer.setExecutor(null);
			httpServer.start();
			System.out.println("http server started in " + HTTP_SERVER_PORT);

			char[] keystorePwd = "tellabs1$".toCharArray();
			HttpsServer hss = HttpsServer.create(new InetSocketAddress(HTTPS_SERVER_PORT), 100); // 设置HTTPS端口这4343
			KeyStore ks = KeyStore.getInstance("JKS"); // 建立证书库
			ks.load(HttpClientTestServer.class.getResourceAsStream("kserver.keystore"), keystorePwd); // 载入证书
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509"); // 建立一个密钥管理工厂
			kmf.init(ks, "server_key_pwd".toCharArray()); // 初始工厂
			SSLContext sslContext = SSLContext.getInstance("SSLv3"); // 建立证书实体
			sslContext.init(kmf.getKeyManagers(), null, null); // 初始化证书
			HttpsConfigurator conf = new HttpsConfigurator(sslContext); // 在https配置
			hss.setHttpsConfigurator(conf); // 在https server载入配置
			hss.setExecutor(null); // creates a default executor
			hss.createContext("/test", new HttpRequestHandler());// 用MyHandler类内处理到/的请求
			hss.start();
			System.out.println("https server started in " + HTTPS_SERVER_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
