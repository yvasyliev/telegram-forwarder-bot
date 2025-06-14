package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandSecurityInterceptorTest {
    @InjectMocks private CommandSecurityInterceptor commandSecurityInterceptor;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;
    @Mock private ProceedingJoinPoint pjp;

    @AfterEach
    void tearDown() throws Throwable {
        verify(pjp).proceed();
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class InterceptTests {
        @AfterEach
        void tearDown() {
            verifyNoInteractions(telegramClient);
        }

        @Test
        void testInterceptMessage() {
            var message = mock(Message.class);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, message));

            verifyNoInteractions(message);
        }

        @Test
        void testInterceptCallbackQuery() {
            var query = mock(CallbackQuery.class);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, query));

            verifyNoInteractions(query);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class InterceptAccessDeniedExceptionTests {
        private static final String UNAUTHORIZED_TEXT = "Unauthorized action";

        @BeforeEach
        void setUp() throws Throwable {
            when(pjp.proceed()).thenThrow(new AccessDeniedException(null));
            when(telegramProperties.unauthorizedActionText()).thenReturn(UNAUTHORIZED_TEXT);
        }

        @Test
        void testInterceptMessage() throws TelegramApiException {
            var chatId = 123L;
            var message = mock(Message.class);
            var sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(UNAUTHORIZED_TEXT)
                    .build();

            when(message.getChatId()).thenReturn(chatId);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, message));

            verify(telegramClient).execute(sendMessage);
        }

        @Test
        void testInterceptCallbackQuery() throws TelegramApiException {
            var callbackQueryId = "123";
            var query = mock(CallbackQuery.class);
            var answerCallbackQuery = AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQueryId)
                    .text(UNAUTHORIZED_TEXT)
                    .build();

            when(query.getId()).thenReturn(callbackQueryId);

            assertDoesNotThrow(() -> commandSecurityInterceptor.intercept(pjp, query));

            verify(telegramClient).execute(answerCallbackQuery);
        }
    }
}