package io.github.yvasyliev.forwarder.telegram.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for unauthorized action messages in the Telegram bot.
 *
 * @param text the text to be sent for unauthorized actions
 */
@ConfigurationProperties("telegram.security.unauthorized-action")
@Validated
public record UnauthorizedActionProperties(@NotBlank String text) {}
