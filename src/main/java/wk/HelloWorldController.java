package wk;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.redirect.RedirectAction;
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

    @Autowired
    ServletContext context;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(HttpServletRequest req, HttpServletResponse res) {
        System.out.println(context.getContextPath());

        Redirect(req, res);
        return "Hello World Developer!!!";
    }

    @RequestMapping("/go")
    @ResponseBody
    public String go(HttpServletRequest req, HttpServletResponse res) {
        System.out.println(context.getContextPath());
        return "Go go go";
    }

    public void Redirect(HttpServletRequest req, HttpServletResponse res) {
        String clientId = "hello";
        String secret = "0f60c296-ff26-4eef-8890-b698c6a5d982";
        String discoveryUri = "http://localhost:8080/auth/realms/master/.well-known/openid-configuration";

        OidcConfiguration config = new OidcConfiguration();
        config.setClientId(clientId);
        config.setSecret(secret);
        config.setDiscoveryURI(discoveryUri);

        J2EContext j2e = new J2EContext(req, res);

        OidcClient client = new OidcClient(config);
        client.setCallbackUrl("http://localhost:8080/go");

        HttpAction action = client.redirect(j2e);
    }
}