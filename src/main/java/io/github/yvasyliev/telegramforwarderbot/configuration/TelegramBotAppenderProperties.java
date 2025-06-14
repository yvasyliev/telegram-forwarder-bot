package io.github.yvasyliev.telegramforwarderbot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("logging.telegram-bot-appender")
public record TelegramBotAppenderProperties(
        @NotBlank String pattern,
        @DefaultValue("warn") @NotBlank String level,
        @DefaultValue("4000") @NotNull Integer maxTextLength
) {
}
