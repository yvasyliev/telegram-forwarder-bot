package io.github.yvasyliev.forwarder.telegram.x.configuration;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;

/**
 * Auto-configuration class for the X module.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "x", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@Import({XFeedEntryMessageSourceConfiguration.class, XPostSenderConfiguration.class, XServicesConfiguration.class})
@ComponentScan("io.github.yvasyliev.forwarder.telegram.x.mapper")
@EnableConfigurationProperties({XApiProperties.class, XProperties.class, XVideoServiceProperties.class})
@EnableIntegration
public class XAutoConfiguration {}
