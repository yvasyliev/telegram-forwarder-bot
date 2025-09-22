package io.github.yvasyliev.telegramforwarder.core.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for Telegram media handling.
 *
 * @param maxPhotoDimensions maximum dimensions (in pixels) for photos to be sent as regular photos
 * @param mediaGroupMaxSize  maximum number of media items allowed in a single media group
 */
@ConfigurationProperties(prefix = "telegram.media")
@Validated
public record TelegramMediaProperties(
        @DefaultValue("10000") @NotNull Integer maxPhotoDimensions,
        @DefaultValue("10") @NotNull Integer mediaGroupMaxSize
) {
}
