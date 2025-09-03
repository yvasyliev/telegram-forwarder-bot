package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.mapper.MessageIdsCallbackDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageIdsCallbackDataSerializeConverterTest {
    @InjectMocks private MessageIdsCallbackDataSerializeConverter callbackDataConverter;
    @Mock private MessageIdsCallbackDataMapper callbackDataMapper;
    
    @Test
    void testConvert() {
        var callbackData = mock(MessageIdsCommandCallbackDataDTO.class);
        var expected = mock(MessageIdsTelegramCommandCallbackDataDTO.class);

        when(callbackDataMapper.map(callbackData)).thenReturn(expected);

        var actual = callbackDataConverter.convert(callbackData);

        assertEquals(expected, actual);
    }
}
