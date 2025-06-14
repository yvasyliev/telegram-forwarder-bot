package io.github.yvasyliev.telegramforwarderbot.databind.util;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RadixSerializeConverterTest {
    @InjectMocks private RadixSerializeConverter radixConverter;
    @Mock private TelegramProperties telegramProperties;

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 8, 10, 16, 36, 62, 64})
    void testConvert(int value) {
        var radix = Character.MAX_RADIX;
        var expected = Integer.toString(value, radix);

        when(telegramProperties.radix()).thenReturn(radix);

        var actual = radixConverter.convert(value);

        assertEquals(expected, actual);
    }
}