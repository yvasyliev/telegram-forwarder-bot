package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendAnimationDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendPhotoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditAnimationMetadataSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditMediaMetadataSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditPhotoMetadataSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Configuration class for Reddit media metadata senders.
 */
@Configuration
public class RedditMediaMetadataSenderConfiguration {
    /**
     * Bean for sending Reddit animation metadata.
     *
     * @param sendAnimationDTOMapper Mapper for sending animation DTOs
     * @param animationSender        Post sender for animations
     * @return Reddit media metadata sender for animations
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationMetadataSender")
    public RedditMediaMetadataSender redditAnimationMetadataSender(
            RedditSendAnimationDTOMapper sendAnimationDTOMapper,
            PostSender<CloseableSupplier<SendAnimationDTO>, Message> animationSender
    ) {
        return new RedditAnimationMetadataSender(sendAnimationDTOMapper, animationSender);
    }

    /**
     * Bean for sending Reddit photo metadata.
     *
     * @param sendPhotoDTOMapper Mapper for sending photo DTOs
     * @param photoSender        Post sender for photos
     * @return Reddit media metadata sender for photos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoMetadataSender")
    public RedditMediaMetadataSender redditPhotoMetadataSender(
            RedditSendPhotoDTOMapper sendPhotoDTOMapper,
            PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender
    ) {
        return new RedditPhotoMetadataSender(sendPhotoDTOMapper, photoSender);
    }
}
