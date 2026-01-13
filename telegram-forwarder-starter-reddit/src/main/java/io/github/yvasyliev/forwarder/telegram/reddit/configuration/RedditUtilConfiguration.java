package io.github.yvasyliev.forwarder.telegram.reddit.configuration;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditInputMediaAnimationDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditInputMediaPhotoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataInputMediaDTOConverter;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataPhotoUrlSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Reddit utility beans.
 */
@Configuration
public class RedditUtilConfiguration {
    /**
     * Creates a {@link RedditMetadataInputMediaDTOConverter} bean.
     *
     * @param inputMediaAnimationDTOMapper the mapper for animated media
     * @param inputMediaPhotoDTOMapper     the mapper for photo media
     * @return a new instance of {@link RedditMetadataInputMediaDTOConverter}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditMetadataInputMediaDTOConverter redditMetadataInputMediaDTOConverter(
            RedditInputMediaAnimationDTOMapper inputMediaAnimationDTOMapper,
            RedditInputMediaPhotoDTOMapper inputMediaPhotoDTOMapper
    ) {
        return new RedditMetadataInputMediaDTOConverter(inputMediaAnimationDTOMapper, inputMediaPhotoDTOMapper);
    }

    /**
     * Creates a {@link RedditMetadataPhotoUrlSelector} bean.
     *
     * @param mediaProperties the Telegram media properties
     * @return a new instance of {@link RedditMetadataPhotoUrlSelector}
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditMetadataPhotoUrlSelector redditMetadataPhotoUrlSelector(TelegramMediaProperties mediaProperties) {
        return new RedditMetadataPhotoUrlSelector(mediaProperties.photoMaxDimensionSum());
    }
}
