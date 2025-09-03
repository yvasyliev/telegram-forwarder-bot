package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;

/**
 * Converts an integer to a string representation in a specified radix.
 *
 * <p>
 * This converter uses the radix defined in {@link TelegramProperties} to convert integers to their string
 * representation.
 */
@RequiredArgsConstructor
public class RadixSerializeConverter extends StdConverter<Integer, String> {
    private final TelegramProperties telegramProperties;

    @Override
    public String convert(Integer value) {
        return Integer.toString(value, telegramProperties.radix());
    }
}
