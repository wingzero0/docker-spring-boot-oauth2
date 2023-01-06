package kit.personal.ssomanagement.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

// @Slf4j
public class LoggingClientCredentialsTokenResponseClient
        implements OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> {

    private static Logger LOG = LoggerFactory.getLogger(LoggingClientCredentialsTokenResponseClient.class);
    private DefaultClientCredentialsTokenResponseClient delegate = new DefaultClientCredentialsTokenResponseClient();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(
            OAuth2ClientCredentialsGrantRequest clientCredentialsGrantRequest) {

        LOG.debug("Sending request {}", clientCredentialsGrantRequest);
        var response = delegate.getTokenResponse(clientCredentialsGrantRequest);
        LOG.debug("Received response {}", response);
        return response;
    }
}