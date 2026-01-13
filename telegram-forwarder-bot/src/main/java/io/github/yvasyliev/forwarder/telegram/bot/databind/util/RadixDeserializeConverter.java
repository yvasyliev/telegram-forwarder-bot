package io.github.yvasyliev.forwarder.telegram.bot.databind.util;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramRadixConverterProperties;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.util.StdConverter;

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
