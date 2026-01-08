package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControlsSenderTest {
    @InjectMocks private PostControlsSender postControlsSender;
    @Mock private TelegramClient telegramClient;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private PostControlsSendMessageProperties postControlsSendMessageProperties;
    @Mock private Message message;
    @Mock private SendMessage sendMessage;

    @BeforeEach
    void setUp() {
        when(sendMessageMapper.map(postControlsSendMessageProperties, List.of(message))).thenReturn(sendMessage);
    }

    private void testSendPostControlKeyboard(Executable executable) throws TelegramApiException {
        assertDoesNotThrow(executable);

        verify(telegramClient).execute(sendMessage);
    }

    @Nested
    class MessagePostControlsSenderTest {
        @Test
        void shouldSendPostControlKeyboard() throws TelegramApiException {
            testSendPostControlKeyboard();
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            testSendPostControlKeyboard();
        }

        private void testSendPostControlKeyboard() throws TelegramApiException {
            PostControlsSenderTest.this.testSendPostControlKeyboard(
                    () -> postControlsSender.sendPostControlKeyboard(message)
            );
        }
    }

    @Nested
    class MessagesPostControlsSenderTest {
        @Test
        void shouldSendPostControlKeyboard() throws TelegramApiException {
            testSendPostControlKeyboard();
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            testSendPostControlKeyboard();
        }

        private void testSendPostControlKeyboard() throws TelegramApiException {
            PostControlsSenderTest.this.testSendPostControlKeyboard(
                    () -> postControlsSender.sendPostControlKeyboard(List.of(message))
            );
        }
    }
}
