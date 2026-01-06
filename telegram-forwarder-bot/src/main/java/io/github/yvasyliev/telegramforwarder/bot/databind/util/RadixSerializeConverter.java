package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramRadixConverterProperties;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.util.StdConverter;

/**
 * Converts an {@link Integer} to its string representation in a specified radix.
 *
 * <p>
 * This converter uses the radix defined in {@link TelegramRadixConverterProperties} to format the integer.
 */
@RequiredArgsConstructor
public class RadixSerializeConverter extends StdConverter<Integer, String> {
    private final TelegramRadixConverterProperties radixConverterProperties;

    @Override
    public String convert(Integer value) {
        return Integer.toString(value, radixConverterProperties.radix());
    }
}
