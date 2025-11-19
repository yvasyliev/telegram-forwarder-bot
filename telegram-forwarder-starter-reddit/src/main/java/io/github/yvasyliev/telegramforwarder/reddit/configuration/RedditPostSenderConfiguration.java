package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditImageAnimationSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMediaGroupSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMetadataSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPhotoSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditUrlSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditVideoAnimationSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditVideoSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;
import java.util.List;

/**
 * Configuration class for Reddit post senders.
 */
@Configuration
public class RedditPostSenderConfiguration {
    /**
     * Creates a {@link RedditPostSender} bean for sending image animations.
     *
     * @param animationSender the {@link PostSender} for animations
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageAnimationSender")
    public RedditPostSender redditImageAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        return new RedditImageAnimationSender(animationSender);
    }

    /**
     * Creates a {@link RedditPostSender} bean for sending URLs.
     *
     * @param urlSender the {@link PostSender} for URLs
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlSender")
    public RedditPostSender redditUrlSender(PostSender<URL, Message> urlSender) {
        return new RedditUrlSender(urlSender);
    }

    /**
     * Creates a {@link RedditPostSender} bean for sending media groups.
     *
     * @param mediaProperties               the Telegram media properties
     * @param redditAnimationMetadataSender the {@link RedditMetadataSender} for animations
     * @param redditPhotoMetadataSender     the {@link RedditMetadataSender} for photos
     * @param mediaGroupSender              the {@link PostSender} for media groups
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupSender")
    public RedditPostSender redditMediaGroupSender(
            TelegramMediaProperties mediaProperties,
            RedditMetadataSender redditAnimationMetadataSender,
            RedditMetadataSender redditPhotoMetadataSender,
            PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender
    ) {
        return new RedditMediaGroupSender(
                mediaProperties,
                redditAnimationMetadataSender,
                redditPhotoMetadataSender,
                mediaGroupSender
        );
    }

    /**
     * Creates a {@link RedditPostSender} bean for sending photos.
     *
     * @param photoSender the {@link PostSender} for photos
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoSender")
    public RedditPostSender redditPhotoSender(PostSender<InputFileDTO, Message> photoSender) {
        return new RedditPhotoSender(photoSender);
    }

    /**
     * Creates a {@link RedditPostSender} bean for sending video animations.
     *
     * @param animationSender the {@link PostSender} for animations
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditVideoAnimationSender")
    public RedditPostSender redditVideoAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        return new RedditVideoAnimationSender(animationSender);
    }

    /**
     * Creates a {@link RedditPostSender} bean for sending videos.
     *
     * @param videoSender           the {@link PostSender} for videos
     * @param redditVideoDownloader the {@link RedditVideoDownloader} for downloading videos
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = " redditVideoSender")
    public RedditPostSender redditVideoSender(
            PostSender<InputFileDTO, Message> videoSender,
            RedditVideoDownloader redditVideoDownloader
    ) {
        return new RedditVideoSender(videoSender, redditVideoDownloader);
    }
}
