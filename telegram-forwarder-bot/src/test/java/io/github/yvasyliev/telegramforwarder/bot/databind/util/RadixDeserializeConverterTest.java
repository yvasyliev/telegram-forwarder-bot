package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramRadixConverterProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RadixDeserializeConverterTest {
    private static final int RADIX = Character.MAX_RADIX;
    private static final RadixDeserializeConverter CONVERTER = new RadixDeserializeConverter(
            new TelegramRadixConverterProperties(RADIX)
    );

    @ParameterizedTest
    @ValueSource(strings = {"10", "16", "2", "8", "36", "64", "0"})
    void testConvert(String value) {
        var expected = Integer.parseInt(value, RADIX);

        var actual = CONVERTER.convert(value);

        assertEquals(expected, actual);
    }
}
