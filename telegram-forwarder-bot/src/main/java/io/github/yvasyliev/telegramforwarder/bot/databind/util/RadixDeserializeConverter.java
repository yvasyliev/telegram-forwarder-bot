package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramRadixConverterProperties;
import lombok.RequiredArgsConstructor;

/**
 * Converter that deserializes a String to an Integer using a specified radix.
 */
@RequiredArgsConstructor
public class RadixDeserializeConverter extends StdConverter<String, Integer> {
    private final TelegramRadixConverterProperties radixConverterProperties;

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value, radixConverterProperties.radix());
    }
}
