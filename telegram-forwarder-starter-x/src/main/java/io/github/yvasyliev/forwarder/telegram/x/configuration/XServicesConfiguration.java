package io.github.yvasyliev.forwarder.telegram.x.configuration;

import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XLastFetchedPostMapper;
import io.github.yvasyliev.forwarder.telegram.x.service.XLastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.x.service.XPostForwarder;
import io.github.yvasyliev.forwarder.telegram.x.service.XPostSenderManager;
import io.github.yvasyliev.forwarder.telegram.x.service.XVideoService;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.x.util.XDescriptionParser;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;

import java.util.List;

/**
 * Configuration class for X services in the Telegram Forwarder application.
 */
@Configuration
public class XServicesConfiguration {
    /**
     * Creates {@link XLastFetchedPostService} bean if not already defined.
     *
     * @param lastFetchedPostService the {@link LastFetchedPostService} bean
     * @param xLastFetchedPostMapper the {@link XLastFetchedPostMapper} bean
     * @return the created {@link XLastFetchedPostService} bean
     */
    @Bean
    @ConditionalOnMissingBean
    public XLastFetchedPostService xLastFetchedPostService(
            LastFetchedPostService lastFetchedPostService,
            XLastFetchedPostMapper xLastFetchedPostMapper
    ) {
        return new XLastFetchedPostService(lastFetchedPostService, xLastFetchedPostMapper);
    }

    /**
     * Creates {@link XPostForwarder} bean if not already defined.
     *
     * @param sources            the {@link FeedEntryMessageSource} beans for each profile
     * @param xProperties        the {@link XProperties} bean containing configuration properties for X profiles
     * @param xPostSenderManager the {@link XPostSenderManager} bean responsible for managing post sender strategies
     * @return the created {@link XPostForwarder} bean
     */
    @Bean
    @ConditionalOnMissingBean
    public XPostForwarder xPostForwarder(
            @Qualifier(XFeedEntryMessageSourceConfiguration.BEAN_NAME) ObjectProvider<FeedEntryMessageSource> sources,
            XProperties xProperties,
            XPostSenderManager xPostSenderManager
    ) {
        var messageSources = xProperties.profiles()
                .stream()
                .map(sources::getObject)
                .toList();
        return new XPostForwarder(messageSources, xPostSenderManager);
    }

    /**
     * Creates {@link XPostSenderManager} bean if not already defined.
     *
     * @param postSenderStrategies the list of {@link XPostSenderStrategy} beans available in the application context
     * @return the created {@link XPostSenderManager} bean
     */
    @Bean
    @ConditionalOnMissingBean
    public XPostSenderManager xPostSenderManager(List<XPostSenderStrategy> postSenderStrategies) {
        return new XPostSenderManager(postSenderStrategies, new XDescriptionParser());
    }

    /**
     * Creates {@link XVideoService} bean if not already defined.
     *
     * @param videoServiceProperties the configuration properties for the video service
     * @return the created {@link XVideoService} bean
     */
    @Bean
    @ConditionalOnMissingBean
    public XVideoService xVideoService(XVideoServiceProperties videoServiceProperties) {
        return new XVideoService(
                videoServiceProperties.uri(),
                videoServiceProperties.xHost(),
                videoServiceProperties.cssSelector()
        );
    }
}
