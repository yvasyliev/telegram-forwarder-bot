package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.ImageAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MediaGroupForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.PhotoForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.UrlForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoForwarder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;
import java.util.List;

/**
 * Configuration class for Reddit post forwarders.
 */
@Configuration
public class ForwarderConfiguration {
    /**
     * Creates a {@link LinkForwarder} bean for forwarding image animations (e.g., GIFs) from Reddit to Telegram.
     *
     * @param animationSender the {@link PostSender} used to send image animations
     * @return a Forwarder for image animations
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageAnimationForwarder")
    public LinkForwarder redditImageAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new ImageAnimationForwarder(animationSender);
    }

    /**
     * Creates a {@link LinkForwarder} bean for forwarding URLs from Reddit to Telegram.
     *
     * @param urlSender the {@link PostSender} used to send URLs
     * @return a {@link LinkForwarder} for URLs
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlForwarder")
    public LinkForwarder redditUrlForwarder(PostSender<URL, Message> urlSender) {
        return new UrlForwarder(urlSender);
    }

    /**
     * Creates a {@link LinkForwarder} bean for forwarding media groups from Reddit to Telegram.
     *
     * @param mediaProperties                  the Telegram media properties
     * @param redditAnimationMetadataForwarder the {@link MetadataForwarder} for Reddit animations
     * @param redditPhotoMetadataForwarder     the {@link MetadataForwarder} for Reddit photos
     * @param mediaGroupSender                 the {@link PostSender} used to send media groups
     * @return a {@link LinkForwarder} for media groups
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupForwarder")
    public LinkForwarder redditMediaGroupForwarder(
            TelegramMediaProperties mediaProperties,
            MetadataForwarder redditAnimationMetadataForwarder,
            MetadataForwarder redditPhotoMetadataForwarder,
            PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender
    ) {
        return new MediaGroupForwarder(
                mediaProperties,
                redditAnimationMetadataForwarder,
                redditPhotoMetadataForwarder,
                mediaGroupSender
        );
    }

    /**
     * Creates a {@link LinkForwarder} bean for forwarding photos from Reddit to Telegram.
     *
     * @param photoSender the {@link PostSender} used to send photos
     * @return a {@link LinkForwarder} for photos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoForwarder")
    public LinkForwarder redditPhotoForwarder(PostSender<InputFileDTO, Message> photoSender) {
        return new PhotoForwarder(photoSender);
    }

    /**
     * Creates a {@link LinkForwarder} bean for forwarding video animations from Reddit to Telegram.
     *
     * @param animationSender the {@link PostSender} used to send video animations
     * @return a {@link LinkForwarder} for video animations
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditVideoAnimationForwarder")
    public LinkForwarder redditVideoAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new VideoAnimationForwarder(animationSender);
    }

    /**
     * Creates a {@link LinkForwarder} bean for forwarding videos from Reddit to Telegram.
     *
     * @param videoSender     the {@link PostSender} used to send videos
     * @param videoDownloader the {@link VideoDownloader} used to download videos
     * @return a {@link LinkForwarder} for videos
     */
    @Bean
    @ConditionalOnMissingBean(name = " redditVideoForwarder")
    public LinkForwarder redditVideoForwarder(
            PostSender<InputFileDTO, Message> videoSender,
            VideoDownloader videoDownloader
    ) {
        return new VideoForwarder(videoSender, videoDownloader);
    }
}
