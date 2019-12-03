package wk;

import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;

public class Program {
    public static void main(String args[]) {
        String clientId = "";
        String secret = "";
        String discoveryUri = "";

        OidcConfiguration config = new OidcConfiguration();
        config.setClientId(clientId);
        config.setSecret(secret);
        config.setDiscoveryURI(discoveryUri);
        OidcClient oidcClient = new OidcClient(config);
    }
}