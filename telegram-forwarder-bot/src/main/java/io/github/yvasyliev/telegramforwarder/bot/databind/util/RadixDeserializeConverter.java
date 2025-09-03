package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;

/**
 * Converts a string representation of an integer in a specified radix to an {@link Integer}.
 *
 * <p>
 * This converter uses the radix defined in {@link TelegramProperties} to parse the string.
 */
@RequiredArgsConstructor
public class RadixDeserializeConverter extends StdConverter<String, Integer> {
    private final TelegramProperties telegramProperties;

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value, telegramProperties.radix());
    }
}
