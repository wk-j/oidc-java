package wk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.pac4j.core.context.J2EContext;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloWorldController {

    String clientId = "hello";
    String clientSecret = "830c9965-2990-4c22-8adc-6af4343b9040";

    @Autowired
    ServletContext context;

    @RequestMapping("/ok")
    @ResponseBody
    public String ok() {
        return "ok";
    }

    @RequestMapping("/login")
    @ResponseBody
    public void login(HttpServletRequest req, HttpServletResponse res) {

        String discoveryUri = "http://localhost:8080/auth/realms/master/.well-known/openid-configuration";
        OidcConfiguration config = new OidcConfiguration();

        config.setClientId(clientId);
        config.setSecret(clientSecret);
        config.setDiscoveryURI(discoveryUri);
        config.setResponseType("code");

        OidcClient client = new OidcClient(config);
        J2EContext j2e = new J2EContext(req, res);

        client.setCallbackUrl("http://localhost:8083/token");
        client.redirect(j2e);
    }

    @RequestMapping("/token")
    @ResponseBody
    public String token(HttpServletRequest req, HttpServletResponse res) throws ClientProtocolException, IOException {
        String code = req.getParameter("code");
        // String state = req.getParameter("state");
        String tokenUrl = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(tokenUrl);

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("code", code));
        // parameters.add(new BasicNameValuePair("state", state));
        parameters.add(new BasicNameValuePair("client_id", clientId));
        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
        parameters.add(new BasicNameValuePair("redirect_uri", "http://localhost:8083/token"));

        httpPost.setEntity(new UrlEncodedFormEntity(parameters));

        HttpResponse response = httpClient.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        return responseBody;
    }
}