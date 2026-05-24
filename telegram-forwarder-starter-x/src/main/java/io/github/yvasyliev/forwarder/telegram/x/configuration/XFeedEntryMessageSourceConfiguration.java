package io.github.yvasyliev.forwarder.telegram.x.configuration;

import io.github.yvasyliev.forwarder.telegram.x.core.io.RssUrlResource;
import io.github.yvasyliev.forwarder.telegram.x.integration.metadata.XMetadataStore;
import io.github.yvasyliev.forwarder.telegram.x.service.XLastFetchedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;

/**
 * A configuration class that creates message sources for fetching feed entries from X (formerly Twitter).
 */
@Configuration
@RequiredArgsConstructor
public class XFeedEntryMessageSourceConfiguration {
    /**
     * The name of the bean for the X feed entry message source.
     */
    public static final String BEAN_NAME = "xFeedEntryMessageSource";

    private final XLastFetchedPostService xLastFetchedPostService;
    private final XApiProperties xApiProperties;

    /**
     * Creates a new instance of {@link FeedEntryMessageSource} for the given profile.
     *
     * @param profile the profile for which to create the message source
     * @return a new instance of {@link FeedEntryMessageSource}
     * @throws MalformedURLException if the URL for the feed cannot be constructed
     */
    @Bean
    @ConditionalOnMissingBean(name = BEAN_NAME)
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public FeedEntryMessageSource xFeedEntryMessageSource(String profile) throws MalformedURLException {
        var url = UriComponentsBuilder.fromUriString(xApiProperties.uriTemplate())
                .build(profile)
                .toURL();
        var source = new FeedEntryMessageSource(new RssUrlResource(url, xApiProperties.userAgent()), profile);

        source.setMetadataStore(new XMetadataStore(xLastFetchedPostService));

        return source;
    }
}
