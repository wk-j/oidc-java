package wk;

import java.util.Collections;

import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.boot.SpringApplication;

public class Program {
    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(HelloWorldController.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }

    static void a() {
        String clientId = "client";
        String secret = "0f60c296-ff26-4eef-8890-b698c6a5d982";
        String discoveryUri = "http://localhost:8080/auth/realms/master/.well-known/openid-configuration";

        // http://wk-macbook.local:8080/auth/realms/alfresco-dbp/.well-known/uma2-configuration

        OidcConfiguration config = new OidcConfiguration();
        config.setClientId(clientId);
        config.setSecret(secret);
        config.setDiscoveryURI(discoveryUri);

        OidcClient client = new OidcClient(config);
        // client.redirect(context)
    }
}