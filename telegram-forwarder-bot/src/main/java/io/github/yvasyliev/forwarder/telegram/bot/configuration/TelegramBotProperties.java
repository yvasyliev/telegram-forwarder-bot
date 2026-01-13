package io.github.yvasyliev.forwarder.telegram.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the Telegram bot.
 */
@ConfigurationProperties("telegram.bot")
@Validated
@Data
public class TelegramBotProperties {
    @Name("token")
    @NotBlank
    private String botToken;

    @NotBlank
    private String version;
}
