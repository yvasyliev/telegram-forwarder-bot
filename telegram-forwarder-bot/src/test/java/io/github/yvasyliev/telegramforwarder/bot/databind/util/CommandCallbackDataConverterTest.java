package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.CommandCallbackDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandCallbackDataConverterTest {
    @InjectMocks private CommandCallbackDataConverter callbackDataConverter;
    @Mock private CommandCallbackDataMapper callbackDataMapper;

    @Test
    void testConvert() {
        var commandCallbackData = mock(RawCommandCallbackData.class);
        var expected = mock(CommandCallbackData.class);

        when(callbackDataMapper.map(commandCallbackData)).thenReturn(expected);

        var actual = callbackDataConverter.convert(commandCallbackData);

        assertEquals(expected, actual);
    }
}
