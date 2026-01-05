package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditClient;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditLinkService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostSenderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.strategy.RedditPostSenderStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Reddit services.
 */
@Configuration
@EnableConfigurationProperties({
        RedditProperties.class,
        RedditVideoDownloaderProperties.class
})
public class RedditServicesConfiguration {
    /**
     * Creates a {@link PostForwarder} bean for Reddit if one is not already present in the context.
     *
     * @param redditInstantPropertyService the Reddit instant property service
     * @param redditClient                 the Reddit client
     * @param redditProperties             the Reddit properties
     * @param redditPostSenderStrategies   the list of Reddit post sender strategies
     * @return a new instance of {@link RedditPostForwarder}
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarder")
    public PostForwarder redditPostForwarder(
            RedditInstantPropertyService redditInstantPropertyService,
            RedditClient redditClient,
            RedditProperties redditProperties,
            List<RedditPostSenderStrategy> redditPostSenderStrategies
    ) {
        return new RedditPostForwarder(
                new RedditLinkService(redditInstantPropertyService, redditClient, redditProperties.subreddit()),
                new RedditPostSenderManager(redditPostSenderStrategies, redditInstantPropertyService)
        );
    }

    /**
     * Creates a {@link RedditInstantPropertyService} bean if one is not already present in the context.
     *
     * @param repository the Reddit instant property repository
     * @return a new instance of {@link RedditInstantPropertyService}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditInstantPropertyService redditInstantPropertyService(RedditInstantPropertyRepository repository) {
        return new RedditInstantPropertyService(repository);
    }

    /**
     * Creates a {@link RedditVideoDownloader} bean if one is not already present in the context.
     *
     * @param videoDownloaderProperties the Reddit video downloader properties
     * @param redditProperties          the Reddit properties
     * @return a new instance of {@link RedditVideoDownloader}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditVideoDownloader redditVideoDownloader(
            RedditVideoDownloaderProperties videoDownloaderProperties,
            RedditProperties redditProperties
    ) {
        return new RedditVideoDownloader(
                videoDownloaderProperties.uri(),
                redditProperties.userAgent(),
                videoDownloaderProperties.cssSelector()
        );
    }
}
