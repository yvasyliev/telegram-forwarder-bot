package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.core.service.PostForwarder;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditLastFetchedPostMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditClient;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditLastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditLinkService;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditPostForwarder;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditPostSenderManager;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditPostSenderStrategy;
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
     * @param redditLastFetchedPostService the service for managing Reddit last fetched post information
     * @param redditClient                 the Reddit client
     * @param redditProperties             the Reddit properties
     * @param redditPostSenderStrategies   the list of Reddit post sender strategies
     * @return a new instance of {@link RedditPostForwarder}
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarder")
    public PostForwarder redditPostForwarder(
            RedditLastFetchedPostService redditLastFetchedPostService,
            RedditClient redditClient,
            RedditProperties redditProperties,
            List<RedditPostSenderStrategy> redditPostSenderStrategies
    ) {
        var subreddit = redditProperties.subreddit();

        return new RedditPostForwarder(
                new RedditLinkService(redditLastFetchedPostService, redditClient, subreddit),
                new RedditPostSenderManager(redditPostSenderStrategies, redditLastFetchedPostService, subreddit)
        );
    }

    /**
     * Creates a {@link RedditLastFetchedPostService} bean if one is not already present in the context.
     *
     * @param lastFetchedPostService      the service for managing last fetched post information
     * @param redditLastFetchedPostMapper the mapper for converting between Reddit-specific last fetched post data and
     *                                    the core last fetched post entity
     * @return a new instance of {@link RedditLastFetchedPostService}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditLastFetchedPostService redditLastFetchedPostService(
            LastFetchedPostService lastFetchedPostService,
            RedditLastFetchedPostMapper redditLastFetchedPostMapper
    ) {
        return new RedditLastFetchedPostService(lastFetchedPostService, redditLastFetchedPostMapper);
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
