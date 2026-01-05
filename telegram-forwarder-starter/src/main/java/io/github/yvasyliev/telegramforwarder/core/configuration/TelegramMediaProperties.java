package io.github.yvasyliev.telegramforwarder.core.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for Telegram media handling.
 *
 * @param photoMaxDimensionSum maximum allowed sum of photo dimensions.
 * @param groupMaxSize         maximum allowed size of media groups.
 */
@ConfigurationProperties(prefix = "telegram.media")
@Validated
public record TelegramMediaProperties(@NotNull Integer photoMaxDimensionSum, @NotNull Integer groupMaxSize) {}
