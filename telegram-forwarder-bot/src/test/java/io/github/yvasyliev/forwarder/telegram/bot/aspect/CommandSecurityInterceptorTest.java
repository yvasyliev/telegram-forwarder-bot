package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.UnauthorizedActionProperties;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.AnswerCallbackQueryMapper;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandSecurityInterceptorTest {
    @InjectMocks private CommandSecurityInterceptor commandSecurityInterceptor;
    @Mock private UnauthorizedActionProperties unauthorizedActionProperties;
    @Mock private SendMessageMapper sendMessageMapper;
    @Mock private AnswerCallbackQueryMapper answerCallbackQueryMapper;
    @Mock private TelegramClient telegramClient;
    @Mock private ProceedingJoinPoint pjp;

    @AfterEach
    void tearDown() throws Throwable {
        verify(pjp).proceed();
    }

    @Nested
    class MessageHandlerInterceptorTest {
        @Mock private Message message;

        @Test
        void shouldProceedMessageHandlingNormally() {
            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, message));
        }

        @Test
        void shouldHandleAccessDeniedException() throws Throwable {
            var sendMessage = mock(SendMessage.class);

            when(pjp.proceed()).thenThrow(AccessDeniedException.class);
            when(sendMessageMapper.map(message, unauthorizedActionProperties)).thenReturn(sendMessage);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, message));

            verify(telegramClient).execute(sendMessage);
        }
    }

    @Nested
    class CallbackQueryHandlerInterceptorTest {
        @Mock private CallbackQuery callbackQuery;

        @Test
        void shouldProceedCallbackQueryHandlingNormally() {
            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, callbackQuery));
        }

        @Test
        void shouldHandleAccessDeniedException() throws Throwable {
            var answerCallbackQuery = mock(AnswerCallbackQuery.class);

            when(pjp.proceed()).thenThrow(AccessDeniedException.class);
            when(answerCallbackQueryMapper.map(callbackQuery, unauthorizedActionProperties))
                    .thenReturn(answerCallbackQuery);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, callbackQuery));

            verify(telegramClient).execute(answerCallbackQuery);
        }
    }
}
