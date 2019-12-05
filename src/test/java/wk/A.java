package wk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class A {
    @Test
    public void aTest() throws ClientProtocolException, IOException {

        String tokenUrl = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";
        String code = "274282dd-daf4-4734-b3bb-218d0d741706.d327edb0-64fb-416b-8a81-22e7246c7691.c4b85c53-ec9f-4adc-8d16-714908f7a610";
        String clientId = "hello";
        String clientSecret = "830c9965-2990-4c22-8adc-6af4343b9040";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(tokenUrl);

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("code", code));
        parameters.add(new BasicNameValuePair("client_id", clientId));
        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
        parameters.add(new BasicNameValuePair("redirect_uri", "http://google.com"));
        parameters.add(new BasicNameValuePair("response_type", "code id_token token"));
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));

        HttpResponse response = httpClient.execute(httpPost);
        // int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println(responseBody);
    }
}