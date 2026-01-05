package io.github.yvasyliev.telegramforwarder.logging.configuration;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * Configuration for the Telegram Bot Appender that sends log messages to a Telegram chat.
 * The appender is enabled by default and can be disabled by setting
 * {@code logging.telegram-bot-appender.enabled=false} property.
 */
@AutoConfiguration
@ConditionalOnProperty(
        name = "logging.telegram-bot-appender.enabled",
        havingValue = BooleanUtils.TRUE,
        matchIfMissing = true
)
@Import({
        LoggingConfiguration.class,
        LoggingMapperConfiguration.class,
        LoggingTemplateContextCustomizerConfiguration.class
})
public class LoggingAutoConfiguration {}
