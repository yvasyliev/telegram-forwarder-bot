package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditClient;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditLinkService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostSenderResolver;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditResolvingPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditPostSenderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Reddit services.
 */
@Configuration
@EnableConfigurationProperties(RedditProperties.class)
public class RedditServiceConfiguration {
    /**
     * Creates a {@link RedditInstantPropertyService} bean if one is not already present.
     *
     * @param repository the {@link RedditInstantPropertyRepository} to be used by the service
     * @return an instance of {@link RedditInstantPropertyService}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditInstantPropertyService redditInstantPropertyService(RedditInstantPropertyRepository repository) {
        return new RedditInstantPropertyService(repository);
    }

    /**
     * Creates a {@link RedditPostSenderResolver} bean if one is not already present.
     *
     * @param redditPostSenderFactories the list of {@link RedditPostSenderFactory} instances
     * @return an instance of {@link RedditPostSenderResolver}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditPostSenderResolver redditPostSenderResolver(
            List<RedditPostSenderFactory> redditPostSenderFactories
    ) {
        return new RedditPostSenderResolver(redditPostSenderFactories);
    }

    /**
     * Creates a {@link RedditResolvingPostSender} bean if one is not already present.
     *
     * @param redditLinkService         the {@link RedditLinkService} to be used by the sender
     * @param redditResolvingPostSender the {@link RedditPostSenderResolver} to resolve the appropriate sender
     * @return an instance of {@link RedditResolvingPostSender}
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarder")
    public PostForwarder redditPostForwarder(
            RedditLinkService redditLinkService,
            RedditResolvingPostSender redditResolvingPostSender
    ) {
        return new RedditPostForwarder(redditLinkService, redditResolvingPostSender);
    }

    /**
     * Creates a {@link RedditVideoDownloader} bean if one is not already present.
     *
     * @param redditProperties the {@link RedditProperties} to be used by the downloader
     * @return an instance of {@link RedditVideoDownloader}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditVideoDownloader redditVideoDownloader(RedditProperties redditProperties) {
        return new RedditVideoDownloader(redditProperties);
    }

    /**
     * Creates a {@link RedditLinkService} bean if one is not already present.
     *
     * @param redditInstantPropertyService the {@link RedditInstantPropertyService} to be used by the service
     * @param redditClient                 the {@link RedditClient} to be used by the service
     * @param redditProperties             the {@link RedditProperties} to be used by the service
     * @return an instance of {@link RedditLinkService}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditLinkService redditLinkService(
            RedditInstantPropertyService redditInstantPropertyService,
            RedditClient redditClient,
            RedditProperties redditProperties
    ) {
        return new RedditLinkService(redditInstantPropertyService, redditClient, redditProperties);
    }

    /**
     * Creates a {@link RedditResolvingPostSender} bean if one is not already present.
     *
     * @param redditPostSenderResolver     the {@link RedditPostSenderResolver} to resolve the appropriate sender
     * @param redditInstantPropertyService the {@link RedditInstantPropertyService} to be used by the sender
     * @return an instance of {@link RedditResolvingPostSender}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditResolvingPostSender redditResolvingPostSender(
            RedditPostSenderResolver redditPostSenderResolver,
            RedditInstantPropertyService redditInstantPropertyService
    ) {
        return new RedditResolvingPostSender(redditPostSenderResolver, redditInstantPropertyService);
    }
}
