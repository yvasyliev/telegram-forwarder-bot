package io.github.yvasyliev.forwarder.telegram.core.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Telegram admin properties.
 *
 * @param id the Telegram admin ID
 */
@ConfigurationProperties("telegram.admin")
@Validated
public record TelegramAdminProperties(@NotNull Long id) {}
