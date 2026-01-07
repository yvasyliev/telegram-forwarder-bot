package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.security.authentication.TelegramAuthenticationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramEventHandlerAuthenticationTest {
    private static final long USER_ID = 123456789L;
    private static final User USER = new User(USER_ID, "testUser", false);
    @InjectMocks private TelegramEventHandlerAuthentication telegramEventHandlerAuthentication;
    @Mock private TelegramAuthenticationManager authenticationManager;

    @Test
    void shouldSetAuthenticateForMessage() {
        var message = Message.builder().from(USER).build();

        shouldSetAuthenticate(() -> telegramEventHandlerAuthentication.authenticate(message));
    }

    @Test
    void shouldSetAuthenticateForCallbackQuery() {
        var callbackQuery = new CallbackQuery();

        callbackQuery.setFrom(USER);

        shouldSetAuthenticate(() -> telegramEventHandlerAuthentication.authenticate(callbackQuery));
    }

    private void shouldSetAuthenticate(Runnable authenticateMethod) {
        var securityContext = mock(SecurityContext.class);
        var expected = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(authenticationManager.authenticate(USER)).thenReturn(expected);

        authenticateMethod.run();

        verify(securityContext).setAuthentication(expected);
    }
}
