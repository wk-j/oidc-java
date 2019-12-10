package wk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.http.callback.NoParameterCallbackUrlResolver;
import org.pac4j.core.http.callback.PathParameterCallbackUrlResolver;
import org.pac4j.oidc.client.KeycloakOidcClient;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.KeycloakOidcConfiguration;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloWorldController {

    String clientId = "java";
    // String clientSecret = "830c9965-2990-4c22-8adc-6af4343b9040";
    String clientSecret = "e1f876fb-eb9d-43a5-9475-9984210c7aca";

    @Autowired
    ServletContext context;

    @RequestMapping("/ok")
    @ResponseBody
    public String ok() {
        return "ok";
    }

    public void show(HttpServletRequest req) {
        Enumeration<String> names = req.getParameterNames();
        ArrayList<String> list = Collections.list(names);
        for (String key : list) {
            String value = req.getParameter(key);
            String fmt = String.format("> %s=%s", key, value);
            System.out.println(fmt);
        }

    }

    @RequestMapping("/login")
    @ResponseBody
    public void login(HttpServletRequest req, HttpServletResponse res) {

        show(req);

        String discoveryUri = "http://localhost:8080/auth/realms/master/.well-known/openid-configuration";
        // OidcConfiguration config = new OidcConfiguration();
        KeycloakOidcConfiguration config = new KeycloakOidcConfiguration();

        config.setRealm("master");
        config.setBaseUri("http://localhost:8080/auth");

        config.setClientId(clientId);
        config.setSecret(clientSecret);
        config.setDiscoveryURI(discoveryUri);
        config.setResponseType("code");
        config.setUseNonce(true);

        // OidcClient client = new OidcClient(config);
        KeycloakOidcClient client = new KeycloakOidcClient(config);

        J2EContext j2e = new J2EContext(req, res);

        client.setCallbackUrl("http://localhost:8083/token");
        // client.setCallbackUrlResolver(new PathParameterCallbackUrlResolver());
        client.setCallbackUrlResolver(new NoParameterCallbackUrlResolver());
        client.redirect(j2e);
    }

    @RequestMapping("/token")
    @ResponseBody
    public String token(HttpServletRequest req, HttpServletResponse res) throws ClientProtocolException, IOException {

        show(req);

        String code = req.getParameter("code");
        String state = req.getParameter("state");
        String cb = "http://localhost:8083/token";
        String url = URLEncoder.encode(cb, "UTF-8");

        String tokenUrl = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";

        HttpClient httpClient = HttpClientBuilder.create().disableContentCompression().build();
        HttpPost httpPost = new HttpPost(tokenUrl);

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("client_id", clientId));
        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
        parameters.add(new BasicNameValuePair("code", code));
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("redirect_uri", cb));
        // parameters.add(new BasicNameValuePair("state", state));

        httpPost.setEntity(new UrlEncodedFormEntity(parameters));

        HttpResponse response = httpClient.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        return responseBody;
    }
}