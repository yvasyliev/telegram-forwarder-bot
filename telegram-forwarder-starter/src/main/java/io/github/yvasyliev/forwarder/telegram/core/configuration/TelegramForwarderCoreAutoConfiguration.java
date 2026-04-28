package io.github.yvasyliev.forwarder.telegram.core.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * Core autoconfiguration class for the Telegram Forwarder application.
 */
@AutoConfiguration
@EnableConfigurationProperties({TelegramAdminProperties.class, TelegramMediaProperties.class})
@Import({
        LastFetchedPostServiceConfiguration.class,
        TelegramForwarderCoreJpaConfiguration.class,
        TelegramForwarderCoreUtilConfiguration.class
})
public class TelegramForwarderCoreAutoConfiguration {}
