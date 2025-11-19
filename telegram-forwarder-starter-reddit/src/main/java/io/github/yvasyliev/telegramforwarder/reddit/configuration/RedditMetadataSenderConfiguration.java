package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditAnimationMetadataSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMetadataSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPhotoMetadataSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;


/**
 * Configuration class for Reddit metadata senders.
 */
@Configuration
public class RedditMetadataSenderConfiguration {
    /**
     * Creates a {@link RedditMetadataSender} bean for sending animation metadata.
     *
     * @param animationSender the {@link PostSender} for animations
     * @return a {@link RedditMetadataSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationMetadataSender")
    public RedditMetadataSender redditAnimationMetadataSender(PostSender<InputFileDTO, Message> animationSender) {
        return new RedditAnimationMetadataSender(animationSender);
    }

    /**
     * Creates a {@link RedditMetadataSender} bean for sending photo metadata.
     *
     * @param mediaProperties the Telegram media properties
     * @param photoSender     the {@link PostSender} for photos
     * @return a {@link RedditMetadataSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoMetadataSender")
    public RedditMetadataSender redditPhotoMetadataSender(
            TelegramMediaProperties mediaProperties,
            PostSender<InputFileDTO, Message> photoSender
    ) {
        return new RedditPhotoMetadataSender(mediaProperties, photoSender);
    }
}
