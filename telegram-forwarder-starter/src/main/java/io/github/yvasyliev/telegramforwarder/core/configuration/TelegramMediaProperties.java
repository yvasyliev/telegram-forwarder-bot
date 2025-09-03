package io.github.yvasyliev.telegramforwarder.core.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "telegram.media")
@Validated
public record TelegramMediaProperties(
        @DefaultValue("10000") @NotNull Integer maxPhotoDimensions,
        @DefaultValue("10") @NotNull Integer mediaGroupMaxSize
) {
}
