package io.github.yvasyliev.forwarder.telegram.bot.databind.util;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramRadixConverterProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.util.StdConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RadixSerializeConverterTest {
    private static final int RADIX = Character.MAX_RADIX;
    private static final StdConverter<Integer, String> CONVERTER = new RadixSerializeConverter(
            new TelegramRadixConverterProperties(RADIX)
    );

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 8, 10, 16, 36, 62, 64})
    void testConvert(int value) {
        var expected = Integer.toString(value, RADIX);

        var actual = CONVERTER.convert(value);

        assertEquals(expected, actual);
    }
}
