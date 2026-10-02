package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditClient;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.client.support.OAuth2RestClientHttpServiceGroupConfigurer;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

/**
 * Auto-configuration for Reddit integration.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "reddit", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@EnableConfigurationProperties({
        RedditProperties.class,
        RedditVideoDownloaderProperties.class
})
@EnableResilientMethods
@ComponentScan({
        "io.github.yvasyliev.forwarder.telegram.reddit.mapper",
        "io.github.yvasyliev.forwarder.telegram.reddit.service",
        "io.github.yvasyliev.forwarder.telegram.reddit.util"
})
@ImportHttpServices(group = RedditClient.REDDIT_GROUP, types = RedditClient.class)
public class RedditAutoConfiguration {
    /**
     * Configures the OAuth2 Rest Client for Reddit.
     *
     * @param clientRegistrationRepository the client registration repository
     * @param authorizedClientService      the authorized client service
     * @return the {@link RestClientHttpServiceGroupConfigurer} for Reddit
     */
    @Bean
    public RestClientHttpServiceGroupConfigurer redditOAuth2RestClientHttpServiceGroupConfigurer(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );

        return OAuth2RestClientHttpServiceGroupConfigurer.from(authorizedClientManager);
    }
}
