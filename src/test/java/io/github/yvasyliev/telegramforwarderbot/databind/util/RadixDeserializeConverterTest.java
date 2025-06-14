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
class RadixDeserializeConverterTest {
    @InjectMocks private RadixDeserializeConverter radixConverter;
    @Mock private TelegramProperties telegramProperties;

    @ParameterizedTest
    @ValueSource(strings = {"10", "16", "2", "8", "36", "64", "0"})
    void testConvert(String value) {
        var radix = Character.MAX_RADIX;
        var expected = Integer.parseInt(value, radix);

        when(telegramProperties.radix()).thenReturn(radix);

        var actual = radixConverter.convert(value);

        assertEquals(expected, actual);
    }
}