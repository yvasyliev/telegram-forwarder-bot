package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditPostSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditHostedVideoSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditImageSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditUrlSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditMediaGroupSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditRichVideoSenderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Configuration class for {@link RedditPostSenderFactory} beans.
 */
@Configuration
public class RedditPostSenderFactoryConfiguration {
    /**
     * Creates a {@link RedditPostSenderFactory} bean for media groups if one is not already present.
     *
     * @param redditMediaGroupSender the {@link RedditPostSender} for media groups
     * @return a {@link RedditPostSenderFactory} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupSenderFactory")
    @Order(0)
    public RedditPostSenderFactory redditMediaGroupSenderFactory(RedditPostSender redditMediaGroupSender) {
        return new RedditMediaGroupSenderFactory(redditMediaGroupSender);
    }

    /**
     * Creates a {@link RedditPostSenderFactory} bean for hosted videos if one is not already present.
     *
     * @param redditVideoSender the {@link RedditPostSender} for videos
     * @return a {@link RedditPostSenderFactory} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditHostedVideoSenderFactory")
    @Order(1)
    public RedditPostSenderFactory redditHostedVideoSenderFactory(RedditPostSender redditVideoSender) {
        return new RedditHostedVideoSenderFactory(redditVideoSender);
    }

    /**
     * Creates a {@link RedditPostSenderFactory} bean for images if one is not already present.
     *
     * @param redditImageAnimationSender the {@link RedditPostSender} for image animations
     * @param redditPhotoSender          the {@link RedditPostSender} for photos
     * @return a {@link RedditPostSenderFactory} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageSenderFactory")
    @Order(2)
    public RedditPostSenderFactory redditImageSenderFactory(
            RedditPostSender redditImageAnimationSender,
            RedditPostSender redditPhotoSender
    ) {
        return new RedditImageSenderFactory(redditImageAnimationSender, redditPhotoSender);
    }

    /**
     * Creates a {@link RedditPostSenderFactory} bean for URLs if one is not already present.
     *
     * @param redditUrlSender the {@link RedditPostSender} for URLs
     * @return a {@link RedditPostSenderFactory} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlSenderFactory")
    @Order(3)
    @SuppressWarnings("checkstyle:MagicNumber")
    public RedditPostSenderFactory redditUrlSenderFactory(RedditPostSender redditUrlSender) {
        return new RedditUrlSenderFactory(redditUrlSender);
    }

    /**
     * Creates a {@link RedditPostSenderFactory} bean for rich videos if one is not already present.
     *
     * @param redditVideoAnimationSender the {@link RedditPostSender} for video animations
     * @param redditUrlSender            the {@link RedditPostSender} for URLs
     * @return a {@link RedditPostSenderFactory} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditRichVideoSenderFactory")
    @Order(4)
    @SuppressWarnings("checkstyle:MagicNumber")
    public RedditPostSenderFactory redditRichVideoSenderFactory(
            RedditPostSender redditVideoAnimationSender,
            RedditPostSender redditUrlSender
    ) {
        return new RedditRichVideoSenderFactory(redditVideoAnimationSender, redditUrlSender);
    }
}
