package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StopCommandTest {
    @InjectMocks private StopCommand stopCommand;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private TelegramClient telegramClient;
    @Mock private ApplicationContext applicationContext;

    @Test
    void testExecute() throws TelegramApiException {
        var message = mock(Message.class);
        var sendMessage = mock(SendMessage.class);

        when(sendMessageMapper.map(message, "shuttingdown")).thenReturn(sendMessage);

        try (var springApplication = mockStatic(SpringApplication.class)) {
            assertDoesNotThrow(() -> stopCommand.execute(message));

            springApplication.verify(() -> SpringApplication.exit(applicationContext));
        }

        verify(telegramClient).execute(sendMessage);
    }
}
