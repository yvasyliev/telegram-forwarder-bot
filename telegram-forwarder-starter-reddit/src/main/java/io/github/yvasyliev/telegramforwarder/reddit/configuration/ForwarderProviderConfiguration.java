package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.HostedVideoForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ImageForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.LinkForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.MediaGroupForwarderProvider;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.RichVideoForwarderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Reddit forwarder providers.
 */
@Configuration
public class ForwarderProviderConfiguration {
    /**
     * Creates a {@link ForwarderProvider} bean for providing a media group forwarder.
     *
     * @param redditMediaGroupForwarder the {@link Forwarder} used to forward media groups
     * @return a {@link ForwarderProvider} for media groups
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupForwarderProvider")
    public ForwarderProvider redditMediaGroupForwarderProvider(Forwarder redditMediaGroupForwarder) {
        return new MediaGroupForwarderProvider(redditMediaGroupForwarder);
    }

    /**
     * Creates a {@link ForwarderProvider} bean for providing a hosted video forwarder.
     *
     * @param redditVideoForwarder the {@link Forwarder} used to forward hosted videos
     * @return a {@link ForwarderProvider} for hosted videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditHostedVideoForwarderProvider")
    public ForwarderProvider redditHostedVideoForwarderProvider(Forwarder redditVideoForwarder) {
        return new HostedVideoForwarderProvider(redditVideoForwarder);
    }

    /**
     * Creates a {@link ForwarderProvider} bean for providing an image forwarder.
     *
     * @param redditImageAnimationForwarder the {@link Forwarder} used to forward image animations
     * @param redditPhotoForwarder          the {@link Forwarder} used to forward photos
     * @return a {@link ForwarderProvider} for images
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageForwarderProvider")
    public ForwarderProvider redditImageForwarderProvider(
            Forwarder redditImageAnimationForwarder,
            Forwarder redditPhotoForwarder
    ) {
        return new ImageForwarderProvider(redditImageAnimationForwarder, redditPhotoForwarder);
    }

    /**
     * Creates a {@link ForwarderProvider} bean for providing a link forwarder.
     *
     * @param redditLinkForwarder the {@link Forwarder} used to forward links
     * @return a {@link ForwarderProvider} for links
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditLinkForwarderProvider")
    public ForwarderProvider redditLinkForwarderProvider(Forwarder redditLinkForwarder) {
        return new LinkForwarderProvider(redditLinkForwarder);
    }

    /**
     * Creates a {@link ForwarderProvider} bean for providing a rich video forwarder.
     *
     * @param redditVideoAnimationForwarder the {@link Forwarder} used to forward video animations
     * @param redditLinkForwarder           the {@link Forwarder} used to forward links
     * @return a {@link ForwarderProvider} for rich videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditRichVideoForwarderProvider")
    public ForwarderProvider redditRichVideoForwarderProvider(
            Forwarder redditVideoAnimationForwarder,
            Forwarder redditLinkForwarder
    ) {
        return new RichVideoForwarderProvider(redditVideoAnimationForwarder, redditLinkForwarder);
    }
}
