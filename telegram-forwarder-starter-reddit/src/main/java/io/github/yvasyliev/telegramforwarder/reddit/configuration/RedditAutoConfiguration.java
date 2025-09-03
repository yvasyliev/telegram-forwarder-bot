package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditService;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
        MetadataForwarderConfiguration.class,
        RedditServiceConfiguration.class
})
public class RedditAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RedditInstantPropertyService redditInstantPropertyService(RedditInstantPropertyRepository repository) {
        return new RedditInstantPropertyService(repository);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarderManager")
    public PostForwarderManager redditPostForwarderManager(
            RedditInstantPropertyService instantPropertyService,
            RedditService redditService,
            RedditProperties redditProperties,
            Forwarder redditMediaGroupForwarder,
            Forwarder redditVideoForwarder,
            Forwarder redditImageAnimationForwarder,
            Forwarder redditPhotoForwarder,
            Forwarder redditLinkForwarder,
            Forwarder redditVideoAnimationForwarder
    ) {
        return new RedditPostForwarderManager(
                instantPropertyService,
                redditService,
                redditProperties,
                redditMediaGroupForwarder,
                redditVideoForwarder,
                redditImageAnimationForwarder,
                redditPhotoForwarder,
                redditLinkForwarder,
                redditVideoAnimationForwarder
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoDownloader videoDownloader(RedditProperties redditProperties) {
        return new VideoDownloader(redditProperties);
    }
}
