package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackDataMapperImplTest {
    @InjectMocks private CallbackDataMapperImpl callbackDataMapper;
    @Mock private MessageIdsCallbackDataMapper messageIdsCallbackDataMapper;

    @Test
    void testMapNull() {
        assertNull(callbackDataMapper.map(null));
    }

    @Test
    void testMap() {
        var callbackData = new MessageIdsTelegramCommandCallbackDataDTO();
        var expected = mock(MessageIdsCommandCallbackDataDTO.class);

        when(messageIdsCallbackDataMapper.map(callbackData)).thenReturn(expected);

        var actual = callbackDataMapper.map(callbackData);

        assertEquals(expected, actual);
        verify(messageIdsCallbackDataMapper).map(callbackData);
    }
}
