package org.basic.java11.httpclient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Test;

/**
 * Created by adam.
 */
public class HttpResponseTest {

    @Test
    public void shouldReturnStatusOKWhenSendGetRequest() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://postman-echo.com/get")).GET().build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertThat(response.body(), not(isEmptyString()));
    }

    @Test
    public void shouldResponseURIDifferentThanRequestUIRWhenRedirect()
            throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://stackoverflow.com"))
                .version(HttpClient.Version.HTTP_1_1).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(request.uri().toString(), equalTo("http://stackoverflow.com"));
        assertThat(response.uri().toString(), equalTo("https://stackoverflow.com/"));
    }

}
