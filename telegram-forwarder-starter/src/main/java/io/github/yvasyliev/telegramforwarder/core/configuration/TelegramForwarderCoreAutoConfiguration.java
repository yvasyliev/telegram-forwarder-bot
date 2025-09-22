package io.github.yvasyliev.telegramforwarder.core.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Core autoconfiguration class for the Telegram Forwarder application.
 */
@AutoConfiguration
@EnableConfigurationProperties(TelegramMediaProperties.class)
public class TelegramForwarderCoreAutoConfiguration {
}
