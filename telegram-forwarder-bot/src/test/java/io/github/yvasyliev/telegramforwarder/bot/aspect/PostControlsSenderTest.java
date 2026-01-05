package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMessageMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(sendMessage);
    }

    @Nested
    class MessagePostControlsSenderTest {
        @Test
        void shouldSendPostControlKeyboard() {
            postControlsSender.sendPostControlKeyboard(message);
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            assertDoesNotThrow(() -> postControlsSender.sendPostControlKeyboard(message));
        }
    }

    @Nested
    class MessagesPostControlsSenderTest {
        @Test
        void shouldSendPostControlKeyboard() {
            postControlsSender.sendPostControlKeyboard(List.of(message));
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            assertDoesNotThrow(() -> postControlsSender.sendPostControlKeyboard(List.of(message)));
        }
    }
}
