package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.LinkForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.HostedVideoForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ImageForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.UrlForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.MediaGroupForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.RichVideoForwarderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Configuration class for Reddit forwarder providers.
 */
@Configuration
public class ForwarderProviderConfiguration {
    /**
     * Creates a {@link LinkForwarderProvider} bean for providing a media group forwarder.
     *
     * @param redditMediaGroupForwarder the {@link LinkForwarder} used to forward media groups
     * @return a {@link LinkForwarderProvider} for media groups
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupForwarderProvider")
    @Order(0)
    public LinkForwarderProvider redditMediaGroupForwarderProvider(LinkForwarder redditMediaGroupForwarder) {
        return new MediaGroupForwarderProvider(redditMediaGroupForwarder);
    }

    /**
     * Creates a {@link LinkForwarderProvider} bean for providing a hosted video forwarder.
     *
     * @param redditVideoForwarder the {@link LinkForwarder} used to forward hosted videos
     * @return a {@link LinkForwarderProvider} for hosted videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditHostedVideoForwarderProvider")
    @Order(1)
    public LinkForwarderProvider redditHostedVideoForwarderProvider(LinkForwarder redditVideoForwarder) {
        return new HostedVideoForwarderProvider(redditVideoForwarder);
    }

    /**
     * Creates a {@link LinkForwarderProvider} bean for providing an image forwarder.
     *
     * @param redditImageAnimationForwarder the {@link LinkForwarder} used to forward image animations
     * @param redditPhotoForwarder          the {@link LinkForwarder} used to forward photos
     * @return a {@link LinkForwarderProvider} for images
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageForwarderProvider")
    @Order(2)
    public LinkForwarderProvider redditImageForwarderProvider(
            LinkForwarder redditImageAnimationForwarder,
            LinkForwarder redditPhotoForwarder
    ) {
        return new ImageForwarderProvider(redditImageAnimationForwarder, redditPhotoForwarder);
    }

    /**
     * Creates a {@link LinkForwarderProvider} bean for providing a link forwarder.
     *
     * @param redditUrlForwarder the {@link LinkForwarder} used to forward links
     * @return a {@link LinkForwarderProvider} for links
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlForwarderProvider")
    @Order(3)
    @SuppressWarnings("checkstyle:MagicNumber")
    public LinkForwarderProvider redditUrlForwarderProvider(LinkForwarder redditUrlForwarder) {
        return new UrlForwarderProvider(redditUrlForwarder);
    }

    /**
     * Creates a {@link LinkForwarderProvider} bean for providing a rich video forwarder.
     *
     * @param redditVideoAnimationForwarder the {@link LinkForwarder} used to forward video animations
     * @param redditUrlForwarder           the {@link LinkForwarder} used to forward links
     * @return a {@link LinkForwarderProvider} for rich videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditRichVideoForwarderProvider")
    @Order(4)
    @SuppressWarnings("checkstyle:MagicNumber")
    public LinkForwarderProvider redditRichVideoForwarderProvider(
            LinkForwarder redditVideoAnimationForwarder,
            LinkForwarder redditUrlForwarder
    ) {
        return new RichVideoForwarderProvider(redditVideoAnimationForwarder, redditUrlForwarder);
    }
}
