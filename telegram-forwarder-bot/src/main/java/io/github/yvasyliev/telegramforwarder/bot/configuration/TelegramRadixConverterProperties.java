package io.github.yvasyliev.telegramforwarder.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the Telegram radix converter.
 *
 * @param radix the radix value for conversion
 */
@ConfigurationProperties("telegram.radix-converter")
@Validated
public record TelegramRadixConverterProperties(@DefaultValue(DEFAULT_RADIX) @NotNull Integer radix) {
    private static final String DEFAULT_RADIX = Character.MAX_RADIX + StringUtils.EMPTY;
}
