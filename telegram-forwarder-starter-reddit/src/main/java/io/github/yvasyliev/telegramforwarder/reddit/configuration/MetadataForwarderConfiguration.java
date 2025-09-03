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

@Configuration
public class MetadataForwarderConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationMetadataForwarder")
    public MetadataForwarder redditAnimationMetadataForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new AnimationMetadataForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoMetadataForwarder")
    public MetadataForwarder redditPhotoMetadataForwarder(
            TelegramMediaProperties mediaProperties,
            PostSender<InputFileDTO, Message> photoSender
    ) {
        return new PhotoMetadataForwarder(mediaProperties, photoSender);
    }
}
