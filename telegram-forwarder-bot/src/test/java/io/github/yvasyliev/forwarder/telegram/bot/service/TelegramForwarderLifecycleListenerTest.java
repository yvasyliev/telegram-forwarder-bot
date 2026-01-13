package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramForwarderLifecycleListenerTest {
    @InjectMocks private TelegramForwarderLifecycleListener telegramForwarderLifecycleListener;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private TelegramClient telegramClient;
    @Mock private SendMessage sendMessage;

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(sendMessage);
    }

    private void testSendNotification(Executable executable) throws TelegramApiException {
        assertDoesNotThrow(executable);

        verify(telegramClient).execute(sendMessage);
    }

    @Nested
    class SendStartupNotificationTest {
        @BeforeEach
        void setUp() {
            when(sendMessageMapper.map(adminProperties, "running")).thenReturn(sendMessage);
        }

        @Test
        void shouldSendNotification() throws TelegramApiException {
            testSendStartupNotification();
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            testSendStartupNotification();
        }

        void testSendStartupNotification() throws TelegramApiException {
            testSendNotification(() -> telegramForwarderLifecycleListener.sendStartupNotification());
        }
    }

    @Nested
    class SendShutdownNotificationTest {
        @BeforeEach
        void setUp() {
            when(sendMessageMapper.map(adminProperties, "shutdown")).thenReturn(sendMessage);
        }

        @Test
        void shouldSendNotification() throws TelegramApiException {
            testSendShutdownNotification();
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(sendMessage)).thenThrow(TelegramApiException.class);

            testSendShutdownNotification();
        }

        void testSendShutdownNotification() throws TelegramApiException {
            testSendNotification(() -> telegramForwarderLifecycleListener.sendShutdownNotification());
        }
    }
}
