package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.ForwarderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditService;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ForwarderProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
     * Creates a {@link ForwarderFactory} bean if one is not already present in the application context.
     *
     * @param redditMediaGroupForwarderProvider  the provider for media group forwarders
     * @param redditHostedVideoForwarderProvider the provider for hosted video forwarders
     * @param redditImageForwarderProvider       the provider for image forwarders
     * @param redditLinkForwarderProvider        the provider for link forwarders
     * @param redditRichVideoForwarderProvider   the provider for rich video forwarders
     * @return a new instance of {@link ForwarderFactory}
     */
    @Bean
    @ConditionalOnMissingBean
    public ForwarderFactory forwarderFactory(
            ForwarderProvider redditMediaGroupForwarderProvider,
            ForwarderProvider redditHostedVideoForwarderProvider,
            ForwarderProvider redditImageForwarderProvider,
            ForwarderProvider redditLinkForwarderProvider,
            ForwarderProvider redditRichVideoForwarderProvider
    ) {
        return new ForwarderFactory(List.of(
                redditMediaGroupForwarderProvider,
                redditHostedVideoForwarderProvider,
                redditImageForwarderProvider,
                redditLinkForwarderProvider,
                redditRichVideoForwarderProvider
        ));
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
    @ConditionalOnMissingBean(name = "redditPostForwarderManager")
    @SuppressWarnings("checkstyle:ParameterNumber")
    public PostForwarderManager redditPostForwarderManager(
            RedditInstantPropertyService instantPropertyService,
            RedditService redditService,
            RedditProperties redditProperties,
            ForwarderFactory forwarderFactory
    ) {
        return new RedditPostForwarderManager(
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
