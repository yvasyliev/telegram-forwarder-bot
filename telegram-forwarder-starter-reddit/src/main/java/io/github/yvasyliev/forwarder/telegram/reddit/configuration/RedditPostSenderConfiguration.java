package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendAnimationDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendPhotoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendUrlDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditAnimationSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPhotoSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditUrlSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditUrlSenderAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Configuration class for Reddit post senders.
 */
@Configuration
public class RedditPostSenderConfiguration {
    /**
     * Bean for adapting Reddit URL sender.
     *
     * @param redditUrlSender Reddit post sender for URLs
     * @return Reddit post sender strategy for URL adaptation
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlSenderAdapter")
    public RedditPostSender redditUrlSenderAdapter(RedditPostSender redditUrlSender) {
        return new RedditUrlSenderAdapter(redditUrlSender);
    }

    /**
     * Bean for sending Reddit animations.
     *
     * @param sendAnimationDTOMapper Mapper for sending animation DTOs
     * @param animationSender        Post sender for animations
     * @return Reddit post sender for animations
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationSender")
    public RedditPostSender redditAnimationSender(
            RedditSendAnimationDTOMapper sendAnimationDTOMapper,
            PostSender<CloseableSupplier<SendAnimationDTO>, Message> animationSender
    ) {
        return new RedditAnimationSender(sendAnimationDTOMapper, animationSender);
    }

    /**
     * Bean for sending Reddit URLs.
     *
     * @param sendUrlDTOMapper Mapper for sending URL DTOs
     * @param urlSender        Post sender for URLs
     * @return Reddit post sender for URLs
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlSender")
    public RedditPostSender redditUrlSender(
            RedditSendUrlDTOMapper sendUrlDTOMapper,
            PostSender<SendUrlDTO, Message> urlSender
    ) {
        return new RedditUrlSender(sendUrlDTOMapper, urlSender);
    }

    /**
     * Bean for sending Reddit photos.
     *
     * @param sendPhotoDTOMapper Mapper for sending photo DTOs
     * @param photoSender        Post sender for photos
     * @return Reddit post sender for photos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoSender")
    public RedditPostSender redditPhotoSender(
            RedditSendPhotoDTOMapper sendPhotoDTOMapper,
            PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender
    ) {
        return new RedditPhotoSender(sendPhotoDTOMapper, photoSender);
    }
}
