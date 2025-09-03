package io.github.yvasyliev.telegramforwarder.core.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(TelegramMediaProperties.class)
public class TelegramForwarderCoreAutoConfiguration {
}
