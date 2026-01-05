package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpMessageCommandTest {
    @InjectMocks private HelpMessageCommand helpMessageCommand;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testExecute() throws TelegramApiException {
        var message = mock(Message.class);
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(message, "help")).thenReturn(sendMessage);

        assertDoesNotThrow(() -> helpMessageCommand.execute(message));

        verify(telegramClient).execute(sendMessage);
    }
}
