package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.AnimationMetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.PhotoMetadataForwarder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Configuration class for Reddit metadata forwarders.
 */
@Configuration
public class MetadataForwarderConfiguration {
    /**
     * Creates a {@link MetadataForwarder} bean for forwarding animation metadata from Reddit to Telegram.
     *
     * @param animationSender the {@link PostSender} used to send animations
     * @return a {@link MetadataForwarder} for animations
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationMetadataForwarder")
    public MetadataForwarder redditAnimationMetadataForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new AnimationMetadataForwarder(animationSender);
    }

    /**
     * Creates a {@link MetadataForwarder} bean for forwarding photo metadata from Reddit to Telegram.
     *
     * @param mediaProperties the Telegram media properties
     * @param photoSender     the {@link PostSender} used to send photos
     * @return a {@link MetadataForwarder} for photos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoMetadataForwarder")
    public MetadataForwarder redditPhotoMetadataForwarder(
            TelegramMediaProperties mediaProperties,
            PostSender<InputFileDTO, Message> photoSender
    ) {
        return new PhotoMetadataForwarder(mediaProperties, photoSender);
    }
}
