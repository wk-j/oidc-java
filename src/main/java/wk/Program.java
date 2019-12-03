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
}