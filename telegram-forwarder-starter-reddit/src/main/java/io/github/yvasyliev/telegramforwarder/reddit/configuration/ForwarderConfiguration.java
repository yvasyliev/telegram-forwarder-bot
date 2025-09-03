package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.ImageAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MediaGroupForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.PhotoForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoForwarder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;
import java.util.List;

@Configuration
public class ForwarderConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "redditImageAnimationForwarder")
    public Forwarder redditImageAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new ImageAnimationForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditLinkForwarder")
    public Forwarder redditLinkForwarder(PostSender<URL, Message> urlSender) {
        return new LinkForwarder(urlSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupForwarder")
    public Forwarder redditMediaGroupForwarder(
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

    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoForwarder")
    public Forwarder redditPhotoForwarder(PostSender<InputFileDTO, Message> photoSender) {
        return new PhotoForwarder(photoSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditVideoAnimationForwarder")
    public Forwarder redditVideoAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new VideoAnimationForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = " redditVideoForwarder")
    public Forwarder redditVideoForwarder(
            PostSender<InputFileDTO, Message> videoSender,
            VideoDownloader videoDownloader
    ) {
        return new VideoForwarder(videoSender, videoDownloader);
    }
}
