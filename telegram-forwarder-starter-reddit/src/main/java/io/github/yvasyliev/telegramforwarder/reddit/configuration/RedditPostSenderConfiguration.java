package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.MediaGroupMetadataSenderStrategy;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.MetadataPartitionSenderStrategyResolver;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.MetadataPartitionSenderStrategy;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditAnimationMetadataSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditHostedVideoSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditImageAnimationSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditImageSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMediaGroupSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMediaGroupSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditMetadataSenderResolver;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPhotoMetadataSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPhotoSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditRichVideoSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditUrlSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditUrlSenderFactory;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditVideoAnimationSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditVideoSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.SingleItemMetadataSenderStrategy;
import io.github.yvasyliev.telegramforwarder.reddit.util.GalleryMetadataPartitioner;
import io.github.yvasyliev.telegramforwarder.reddit.util.MetadataInputMediaDTOConverter;
import io.github.yvasyliev.telegramforwarder.reddit.util.MetadataPhotoUrlSelector;
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
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class RedditPostSenderConfiguration {
    /**
     * Creates a {@link RedditPostSender} for sending URL posts.
     *
     * @param urlSender the {@link PostSender} for URL and Message
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditUrlSender")
    public RedditPostSender redditUrlSender(PostSender<URL, Message> urlSender) {
        return new RedditUrlSender(urlSender);
    }

    /**
     * Creates a {@link RedditPostSender} for sending media group posts.
     *
     * @param telegramMediaProperties          the Telegram media properties
     * @param mediaGroupMetadataSenderStrategy the metadata sender strategy for media groups
     * @param singleItemMetadataSenderStrategy the metadata sender strategy for single items
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupSender")
    public RedditPostSender redditMediaGroupSender(
            TelegramMediaProperties telegramMediaProperties,
            MetadataPartitionSenderStrategy mediaGroupMetadataSenderStrategy,
            MetadataPartitionSenderStrategy singleItemMetadataSenderStrategy
    ) {
        return new RedditMediaGroupSender(
                new GalleryMetadataPartitioner(telegramMediaProperties.groupMaxSize()),
                new MetadataPartitionSenderStrategyResolver(
                        singleItemMetadataSenderStrategy,
                        mediaGroupMetadataSenderStrategy
                )
        );
    }

    /**
     * Creates a {@link RedditPostSender} for sending video posts.
     *
     * @param videoSender           the {@link PostSender} for video and Message
     * @param redditVideoDownloader the Reddit video downloader
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditVideoSender")
    public RedditPostSender redditVideoSender(
            PostSender<InputFileDTO, Message> videoSender,
            RedditVideoDownloader redditVideoDownloader
    ) {
        return new RedditVideoSender(videoSender, redditVideoDownloader);
    }

    /**
     * Creates a {@link RedditPostSender} for sending image animation posts.
     *
     * @param animationSender the {@link PostSender} for animation and Message
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditImageAnimationSender")
    public RedditPostSender redditImageAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        return new RedditImageAnimationSender(animationSender);
    }

    /**
     * Creates a {@link RedditPostSender} for sending photo posts.
     *
     * @param photoSender the {@link PostSender} for photo and Message
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoSender")
    public RedditPostSender redditPhotoSender(PostSender<InputFileDTO, Message> photoSender) {
        return new RedditPhotoSender(photoSender);
    }

    /**
     * Creates a {@link RedditPostSender} for sending video animation posts.
     *
     * @param animationSender the {@link PostSender} for animation and Message
     * @return a {@link RedditPostSender} instance
     */
    @Bean
    @ConditionalOnMissingBean(name = "redditVideoAnimationSender")
    public RedditPostSender redditVideoAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        return new RedditVideoAnimationSender(animationSender);
    }

    /**
     * Configuration class for metadata partition sender strategies.
     */
    @Configuration
    public static class MetadataPartitionSenderStrategyConfiguration {
        /**
         * Creates a {@link MetadataPhotoUrlSelector} bean.
         *
         * @param telegramMediaProperties the Telegram media properties
         * @return a {@link MetadataPhotoUrlSelector} instance
         */
        @Bean
        @ConditionalOnMissingBean
        public MetadataPhotoUrlSelector metadataPhotoUrlSelector(TelegramMediaProperties telegramMediaProperties) {
            return new MetadataPhotoUrlSelector(telegramMediaProperties.photoMaxDimensionSum());
        }

        /**
         * Creates a {@link MetadataPartitionSenderStrategy} for media groups.
         *
         * @param photoUrlSelector the {@link MetadataPhotoUrlSelector}
         * @param mediaGroupSender the {@link PostSender} for list of InputMediaDTO and list of Message
         * @return a {@link MetadataPartitionSenderStrategy} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "mediaGroupMetadataSenderStrategy")
        public MetadataPartitionSenderStrategy mediaGroupMetadataSenderStrategy(
                MetadataPhotoUrlSelector photoUrlSelector,
                PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender
        ) {
            return new MediaGroupMetadataSenderStrategy(
                    new MetadataInputMediaDTOConverter(photoUrlSelector),
                    mediaGroupSender
            );
        }

        /**
         * Creates a {@link MetadataPartitionSenderStrategy} for single items.
         *
         * @param animationSender  the {@link PostSender} for animation and Message
         * @param photoUrlSelector the {@link MetadataPhotoUrlSelector}
         * @param photoSender      the {@link PostSender} for InputFileDTO and Message
         * @return a {@link MetadataPartitionSenderStrategy} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "singleItemMetadataSenderStrategy")
        public MetadataPartitionSenderStrategy singleItemMetadataSenderStrategy(
                PostSender<InputFileDTO, Message> animationSender,
                MetadataPhotoUrlSelector photoUrlSelector,
                PostSender<InputFileDTO, Message> photoSender
        ) {
            return new SingleItemMetadataSenderStrategy(new RedditMetadataSenderResolver(
                    new RedditAnimationMetadataSender(animationSender),
                    new RedditPhotoMetadataSender(photoUrlSelector, photoSender)
            ));
        }
    }

    /**
     * Configuration class for Reddit post sender factories.
     */
    @Configuration
    public static class RedditPostSenderFactoryConfiguration {
        /**
         * Creates a {@link RedditPostSenderFactory} for media group posts.
         *
         * @param redditMediaGroupSender the {@link RedditPostSender} for media groups
         * @return a {@link RedditPostSenderFactory} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "redditMediaGroupSenderFactory")
        public RedditPostSenderFactory redditMediaGroupSenderFactory(RedditPostSender redditMediaGroupSender) {
            return new RedditMediaGroupSenderFactory(redditMediaGroupSender);
        }

        /**
         * Creates a {@link RedditPostSenderFactory} for hosted video posts.
         *
         * @param redditVideoSender the {@link RedditPostSender} for videos
         * @return a {@link RedditPostSenderFactory} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "redditHostedVideoSenderFactory")
        public RedditPostSenderFactory redditHostedVideoSenderFactory(RedditPostSender redditVideoSender) {
            return new RedditHostedVideoSenderFactory(redditVideoSender);
        }

        /**
         * Creates a {@link RedditPostSenderFactory} for image posts.
         *
         * @param redditImageAnimationSender the {@link RedditPostSender} for image animations
         * @param redditPhotoSender          the {@link RedditPostSender} for photos
         * @return a {@link RedditPostSenderFactory} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "redditImageSenderFactory")
        public RedditPostSenderFactory redditImageSenderFactory(
                RedditPostSender redditImageAnimationSender,
                RedditPostSender redditPhotoSender
        ) {
            return new RedditImageSenderFactory(redditImageAnimationSender, redditPhotoSender);
        }

        /**
         * Creates a {@link RedditPostSenderFactory} for URL posts.
         *
         * @param redditUrlSender the {@link RedditPostSender} for URLs
         * @return a {@link RedditPostSenderFactory} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "redditUrlSenderFactory")
        public RedditPostSenderFactory redditUrlSenderFactory(RedditPostSender redditUrlSender) {
            return new RedditUrlSenderFactory(redditUrlSender);
        }

        /**
         * Creates a {@link RedditPostSenderFactory} for rich video posts.
         *
         * @param redditVideoAnimationSender the {@link RedditPostSender} for video animations
         * @param redditUrlSender            the {@link RedditPostSender} for URLs
         * @return a {@link RedditPostSenderFactory} instance
         */
        @Bean
        @ConditionalOnMissingBean(name = "redditRichVideoSenderFactory")
        public RedditPostSenderFactory redditRichVideoSenderFactory(
                RedditPostSender redditVideoAnimationSender,
                RedditPostSender redditUrlSender
        ) {
            return new RedditRichVideoSenderFactory(redditVideoAnimationSender, redditUrlSender);
        }
    }
}
