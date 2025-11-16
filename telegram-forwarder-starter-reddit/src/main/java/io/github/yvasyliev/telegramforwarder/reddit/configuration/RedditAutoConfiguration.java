package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.LinkForwarderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.LinkForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditService;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.LinkForwarderProvider;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

/**
 * Configuration class for setting up the Reddit service client.
 *
 * <p>
 * This configuration uses OAuth2 for authentication and sets up a {@link RedditService} bean
 * that can be used to interact with the Reddit API.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "reddit", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@EnableConfigurationProperties(RedditProperties.class)
@EnableJpaRepositories("io.github.yvasyliev.telegramforwarder.reddit.repository")
@EntityScan("io.github.yvasyliev.telegramforwarder.reddit.entity")
@Import({
        ForwarderConfiguration.class,
        ForwarderProviderConfiguration.class,
        MetadataForwarderConfiguration.class,
        RedditServiceConfiguration.class
})
public class RedditAutoConfiguration {
    /**
     * Creates a {@link RedditInstantPropertyService} bean if one is not already present in the application context.
     *
     * @param repository the repository used by the service
     * @return a new instance of {@link RedditInstantPropertyService}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditInstantPropertyService redditInstantPropertyService(RedditInstantPropertyRepository repository) {
        return new RedditInstantPropertyService(repository);
    }

    /**
     * Creates a {@link LinkForwarderFactory} bean if one is not already present in the application context.
     *
     * @param forwarderProviders the list of forwarder providers
     * @return a new instance of {@link LinkForwarderFactory}
     */
    @Bean
    @ConditionalOnMissingBean
    public LinkForwarderFactory redditLinkForwarderFactory(List<LinkForwarderProvider> forwarderProviders) {
        return new LinkForwarderFactory(forwarderProviders);
    }

    /**
     * Creates a {@link PostForwarderManager} bean for managing Reddit post forwarding.
     *
     * @param instantPropertyService the service for managing instant properties
     * @param redditService          the Reddit service client
     * @param redditProperties       the Reddit configuration properties
     * @param forwarderFactory       the factory for obtaining appropriate forwarders
     * @return a new instance of {@link PostForwarderManager} for Reddit
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditLinkForwarderManager")
    @SuppressWarnings("checkstyle:ParameterNumber")
    public PostForwarderManager redditLinkForwarderManager(
            RedditInstantPropertyService instantPropertyService,
            RedditService redditService,
            RedditProperties redditProperties,
            LinkForwarderFactory forwarderFactory
    ) {
        return new LinkForwarderManager(
                instantPropertyService,
                redditService,
                redditProperties,
                forwarderFactory
        );
    }

    /**
     * Creates a {@link VideoDownloader} bean for downloading videos from Reddit.
     *
     * @param redditProperties the Reddit configuration properties
     * @return a new instance of {@link VideoDownloader}
     */
    @Bean
    @ConditionalOnMissingBean
    public VideoDownloader videoDownloader(RedditProperties redditProperties) {
        return new VideoDownloader(redditProperties);
    }
}
