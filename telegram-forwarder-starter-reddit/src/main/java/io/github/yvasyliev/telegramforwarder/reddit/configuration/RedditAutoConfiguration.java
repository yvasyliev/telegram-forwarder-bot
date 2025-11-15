package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditService;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(prefix = "reddit", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@EnableConfigurationProperties(RedditProperties.class)
@EnableJpaRepositories("io.github.yvasyliev.telegramforwarder.reddit.repository")
@EntityScan("io.github.yvasyliev.telegramforwarder.reddit.entity")
@Import({
        ForwarderConfiguration.class,
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
     * Creates a {@link PostForwarderManager} bean for managing Reddit post forwarding.
     *
     * @param instantPropertyService        the service for managing instant properties
     * @param redditService                 the Reddit service client
     * @param redditProperties              the Reddit configuration properties
     * @param redditMediaGroupForwarder     the forwarder for media groups
     * @param redditVideoForwarder          the forwarder for videos
     * @param redditImageAnimationForwarder the forwarder for image animations
     * @param redditPhotoForwarder          the forwarder for photos
     * @param redditLinkForwarder           the forwarder for links
     * @param redditVideoAnimationForwarder the forwarder for video animations
     * @return a new instance of {@link PostForwarderManager} for Reddit
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarderManager")
    @SuppressWarnings("checkstyle:ParameterNumber")
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
