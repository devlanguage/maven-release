
import java.net.Socket;
import java.net.URL;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@SuppressWarnings({ "unused", "deprecation" })
public class GetRequestClient {

    static org.apache.log4j.Logger logger = Logger.getLogger("Http Test");

    public static void main(String[] args) {
        try {
            java.net.URI uri = new java.net.URI("http://asdf:23@www.ietf.org/rfc/rfc2732.txt");
            // uri = new java.net.URI("mailto:java-net@java.sun.com");
            // uri = new java.net.URI("news:comp.lang.java");
            logger.log(Level.ALL, uri.getHost());
            logger.log(Level.ALL, uri.getAuthority());
            logger.log(Level.ALL, uri.getFragment());
            logger.log(Level.ALL, uri.getPath());
            logger.log(Level.ALL, uri.getQuery());
            logger.log(Level.ALL, uri.getScheme());
            logger.log(Level.ALL, uri.getSchemeSpecificPart());
            logger.log(Level.ALL, uri.getUserInfo());

            logger.log(Level.ALL, uri.getRawAuthority());

            java.net.URL url = new URL("http", "localhost", 8080, "/test/getMethod.jsp");
            // url = new URL("http", "www.tellabs.com", 80, "/");
            logger.log(Level.DEBUG, "Access the Server: " + url.getPath());
            // InputStream ins = url.openStream();
            // logger.log(Level.ALL,StringUtil.streamToStringBuffer(ins));

            HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
                    .add(new RequestConnControl()).add(new RequestUserAgent("Test/1.1")).add(new RequestExpectContinue(true)).build();

            HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

            HttpCoreContext coreContext = HttpCoreContext.create();
            HttpHost host = new HttpHost(HttpClientTestServer.HTTP_SERVER_NAME, HttpClientTestServer.HTTP_SERVER_PORT);
            coreContext.setTargetHost(host);

            DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
            ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

            try {

                String[] targets = { "/test", "/servlets-examples/servlet/RequestInfoExample", "/somewhere%20in%20pampa" };

                for (int i = 0; i < targets.length; i++) {
                    if (!conn.isOpen()) {
                        Socket socket = new Socket(host.getHostName(), host.getPort());
                        conn.bind(socket);
                    }
                    BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);
                    System.out.println(">> Request URI: " + request.getRequestLine().getUri());

                    httpexecutor.preProcess(request, httpproc, coreContext);
                    HttpResponse response = httpexecutor.execute(request, conn, coreContext);
                    httpexecutor.postProcess(response, httpproc, coreContext);

                    System.out.println("<< Response: " + response.getStatusLine());
                    System.out.println(EntityUtils.toString(response.getEntity()));
                    System.out.println("==============");
                    if (!connStrategy.keepAlive(response, coreContext)) {
                        conn.close();
                    } else {
                        System.out.println("Connection kept alive...");
                    }
                }
            } finally {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
