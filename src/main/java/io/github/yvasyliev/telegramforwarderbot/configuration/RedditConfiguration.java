package io.github.yvasyliev.telegramforwarderbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yvasyliev.telegramforwarderbot.reddit.service.RedditService;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;
import java.util.Objects;

/**
 * Configuration class for setting up the Reddit service client.
 *
 * <p>
 * This configuration uses OAuth2 for authentication and sets up a {@link RedditService} bean
 * that can be used to interact with the Reddit API.
 */
@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class RedditConfiguration {
    /**
     * Creates a {@link RedditService} bean configured with OAuth2 client properties and
     * a custom HTTP request interceptor.
     *
     * @param oAuth2ClientProperties  the OAuth2 client properties
     * @param redditProperties        the Reddit properties containing API host and user agent
     * @param objectMapper            the Jackson ObjectMapper for JSON serialization/deserialization
     * @param boolIntArgumentResolver custom argument resolver for boolean to integer conversion
     * @return a configured {@link RedditService} instance
     */
    @Bean
    public RedditService redditService(
            OAuth2ClientProperties oAuth2ClientProperties,
            RedditProperties redditProperties,
            ObjectMapper objectMapper,
            HttpServiceArgumentResolver boolIntArgumentResolver
    ) {
        var clientRegistrations = List.copyOf(new OAuth2ClientPropertiesMapper(oAuth2ClientProperties)
                .asClientRegistrations()
                .values()
        );
        var clientRegistrationRepository = new InMemoryClientRegistrationRepository(clientRegistrations);
        var authorizedClientService = new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
        var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );
        var oAuth2ClientHttpRequestInterceptor = new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        oAuth2ClientHttpRequestInterceptor.setClientRegistrationIdResolver(request -> "reddit");

        var restClient = RestClient.builder()
                .baseUrl(redditProperties.apiHost())
                .defaultHeader(HttpHeaders.USER_AGENT, redditProperties.userAgent())
                .requestInterceptor(oAuth2ClientHttpRequestInterceptor)
                .messageConverters(converters -> Objects.requireNonNull(CollectionUtils.findValueOfType(
                                converters,
                                AbstractJackson2HttpMessageConverter.class
                        )).setObjectMapper(objectMapper)
                )
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                .customArgumentResolver(boolIntArgumentResolver)
                .build();
        return httpServiceProxyFactory.createClient(RedditService.class);
    }
}
