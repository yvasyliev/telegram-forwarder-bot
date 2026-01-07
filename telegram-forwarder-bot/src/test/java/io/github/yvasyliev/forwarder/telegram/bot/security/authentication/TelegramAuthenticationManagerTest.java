package io.github.yvasyliev.forwarder.telegram.bot.security.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramAuthenticationManagerTest {
    @InjectMocks private TelegramAuthenticationManager telegramAuthenticationManager;
    @Mock private AuthenticationManager authenticationManager;

    @Test
    void shouldAuthenticateUser() {
        var user = mock(User.class);
        var expected = mock(Authentication.class);

        when(authenticationManager.authenticate(new TelegramAuthentication(user))).thenReturn(expected);

        var actual = telegramAuthenticationManager.authenticate(user);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnAnonymousAuthentication() {
        var user = mock(User.class);
        var expected = TelegramAnonymousAuthentication.create(user);

        when(authenticationManager.authenticate(new TelegramAuthentication(user)))
                .thenThrow(UsernameNotFoundException.class);

        var actual = telegramAuthenticationManager.authenticate(user);

        assertEquals(expected, actual);
    }
}
