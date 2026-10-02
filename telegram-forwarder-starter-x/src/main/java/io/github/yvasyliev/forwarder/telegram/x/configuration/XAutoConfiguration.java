package io.github.yvasyliev.forwarder.telegram.x.configuration;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.x.service.XPostForwarder;
import io.github.yvasyliev.forwarder.telegram.x.service.XPostSenderManager;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;

/**
 * Auto-configuration class for the X module.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "x", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@Import(XFeedEntryMessageSourceConfiguration.class)
@ComponentScan({
        "io.github.yvasyliev.forwarder.telegram.x.mapper",
        "io.github.yvasyliev.forwarder.telegram.x.service",
        "io.github.yvasyliev.forwarder.telegram.x.util"
})
@EnableConfigurationProperties({XApiProperties.class, XProperties.class, XVideoServiceProperties.class})
@EnableIntegration
public class XAutoConfiguration {
    /**
     * Creates {@link XPostForwarder} bean if not already defined.
     *
     * @param sources            the {@link MessageSource<SyndEntry>} beans for each profile
     * @param xProperties        the {@link XProperties} bean containing configuration properties for X profiles
     * @param xPostSenderManager the {@link XPostSenderManager} bean responsible for managing post sender strategies
     * @return the created {@link XPostForwarder} bean
     */
    @Bean
    public XPostForwarder xPostForwarder(
            @Qualifier(XFeedEntryMessageSourceConfiguration.BEAN_NAME) ObjectProvider<MessageSource<SyndEntry>> sources,
            XProperties xProperties,
            XPostSenderManager xPostSenderManager
    ) {
        var messageSources = xProperties.profiles()
                .stream()
                .map(sources::getObject)
                .toList();
        return new XPostForwarder(messageSources, xPostSenderManager);
    }
}
