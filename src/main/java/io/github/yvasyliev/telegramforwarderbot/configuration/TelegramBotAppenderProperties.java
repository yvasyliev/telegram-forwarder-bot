package io.github.yvasyliev.telegramforwarderbot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for the Telegram Bot appender.
 * This class holds the necessary properties to configure the logging appender
 * that sends log messages to a Telegram bot.
 *
 * @param pattern       the log message pattern to be used
 * @param level         the minimum log level for messages to be sent
 * @param maxTextLength the maximum length of text messages sent to Telegram
 */
@ConfigurationProperties("logging.telegram-bot-appender")
public record TelegramBotAppenderProperties(
        @NotBlank String pattern,
        @DefaultValue("warn") @NotBlank String level,
        @DefaultValue("4000") @NotNull Integer maxTextLength
) {
}
