package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlSenderTest {
    @InjectMocks private UrlSender urlSender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var sendUrlDTO = mock(SendUrlDTO.class);
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(sendUrlDTO, adminProperties)).thenReturn(sendMessage);

        assertDoesNotThrow(() -> urlSender.send(sendUrlDTO));

        verify(telegramClient).execute(sendMessage);
    }
}
