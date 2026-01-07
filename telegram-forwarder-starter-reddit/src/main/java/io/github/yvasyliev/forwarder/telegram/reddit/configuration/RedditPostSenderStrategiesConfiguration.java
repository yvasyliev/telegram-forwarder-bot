package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendMediaGroupDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendVideoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditMediaMetadataSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition.RedditMediaGroupMetadataSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition.RedditMediaMetadataSenderManager;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditGalleryDataSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditHostedVideoSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditImageSenderManager;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditRichVideoSenderManager;
import io.github.yvasyliev.forwarder.telegram.reddit.util.GalleryMetadataPartitioner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

/**
 * Configuration class for Reddit post sender strategies.
 */
@Configuration
public class RedditPostSenderStrategiesConfiguration {
    /**
     * Bean for sending Reddit gallery data.
     *
     * @param mediaProperties               Telegram media properties
     * @param sendMediaGroupDTOMapper       Mapper for sending media group DTOs
     * @param mediaGroupSender              Post sender for media groups
     * @param redditAnimationMetadataSender Reddit media metadata sender for animations
     * @param redditPhotoMetadataSender     Reddit media metadata sender for photos
     * @return Reddit post sender strategy for gallery data
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditGalleryDataSender")
    public RedditPostSenderStrategy redditGalleryDataSender(
            TelegramMediaProperties mediaProperties,
            RedditSendMediaGroupDTOMapper sendMediaGroupDTOMapper,
            PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> mediaGroupSender,
            RedditMediaMetadataSender redditAnimationMetadataSender,
            RedditMediaMetadataSender redditPhotoMetadataSender
    ) {
        var galleryMetadataPartitioner = new GalleryMetadataPartitioner(mediaProperties.groupMaxSize());
        var mediaGroupMetadataSender = new RedditMediaGroupMetadataSender(sendMediaGroupDTOMapper, mediaGroupSender);
        var mediaMetadataSender = new RedditMediaMetadataSenderManager(
                redditAnimationMetadataSender,
                redditPhotoMetadataSender
        );

        return new RedditGalleryDataSender(galleryMetadataPartitioner, mediaMetadataSender, mediaGroupMetadataSender);
    }

    /**
     * Bean for sending Reddit hosted videos.
     *
     * @param sendVideoDTOMapper Mapper for sending video DTOs
     * @param videoSender        Post sender for videos
     * @return Reddit post sender strategy for hosted videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditHostedVideoSender")
    public RedditPostSenderStrategy redditHostedVideoSender(
            RedditSendVideoDTOMapper sendVideoDTOMapper,
            PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender
    ) {
        return new RedditHostedVideoSender(sendVideoDTOMapper, videoSender);
    }

    /**
     * Bean for sending Reddit images.
     *
     * @param redditAnimationSender Reddit post sender for animations
     * @param redditPhotoSender     Reddit post sender for photos
     * @return Reddit post sender strategy for images
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageSenderManager")
    public RedditPostSenderStrategy redditImageSenderManager(
            RedditPostSender redditAnimationSender,
            RedditPostSender redditPhotoSender
    ) {
        return new RedditImageSenderManager(redditAnimationSender, redditPhotoSender);
    }

    /**
     * Bean for sending Reddit rich videos.
     *
     * @param redditAnimationSender Reddit post sender for animations
     * @param redditUrlSender       Reddit post sender for URLs
     * @return Reddit post sender strategy for rich videos
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditRichVideoSenderManager")
    public RedditPostSenderStrategy redditRichVideoSenderManager(
            RedditPostSender redditAnimationSender,
            RedditPostSender redditUrlSender
    ) {
        return new RedditRichVideoSenderManager(redditAnimationSender, redditUrlSender);
    }
}
