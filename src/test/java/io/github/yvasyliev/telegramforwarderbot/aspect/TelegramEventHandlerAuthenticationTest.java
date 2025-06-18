package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.util.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramEventHandlerAuthenticationTest {
    private static final long USER_ID = 123456789L;
    private static final User TELEGRAM_USER = new User(USER_ID, "testUser", false);
    private static final Authentication USERNAME_PASSWORD_AUTHENTICATION_TOKEN =
            new UsernamePasswordAuthenticationToken(USER_ID, StringUtils.EMPTY, List.of());
    @InjectMocks private TelegramEventHandlerAuthentication telegramEventHandlerAuthentication;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private SecurityContext securityContext;
    private MockedStatic<SecurityContextHolder> securityContextHolder;

    @BeforeEach
    void setUp() {
        securityContextHolder = mockStatic(SecurityContextHolder.class);

        securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
    }

    @AfterEach
    void tearDown() {
        securityContextHolder.close();
    }

    @Nested
    class UserAuthenticationTests {
        @Mock
        private Authentication expectedAuthentication;

        @BeforeEach
        void setUp() {
            when(authenticationManager.authenticate(USERNAME_PASSWORD_AUTHENTICATION_TOKEN))
                    .thenReturn(expectedAuthentication);
        }

        @AfterEach
        void tearDown() {
            verify(securityContext).setAuthentication(expectedAuthentication);
        }

        @Test
        void shouldAuthenticateByMessage() {
            var message = Message.builder().from(TELEGRAM_USER).build();

            telegramEventHandlerAuthentication.authenticate(message);
        }

        @Test
        void shouldAuthenticateByCallbackQuery() {
            var callbackQuery = new CallbackQuery();

            callbackQuery.setFrom(TELEGRAM_USER);

            telegramEventHandlerAuthentication.authenticate(callbackQuery);
        }
    }

    @Nested
    class AnonymousAuthenticationTests {
        private static final org.springframework.security.core.userdetails.User INTERNAL_USER =
                new org.springframework.security.core.userdetails.User(
                        String.valueOf(USER_ID),
                        AuthUtils.DEFAULT_PASSWORD,
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                );
        private static final Authentication EXPECTED_AUTHENTICATION = new AnonymousAuthenticationToken(
                INTERNAL_USER.getUsername(), INTERNAL_USER, INTERNAL_USER.getAuthorities()
        );
        private MockedStatic<AuthUtils> authUtils;

        @BeforeEach
        void setUp() {
            authUtils = mockStatic(AuthUtils.class);

            when(authenticationManager.authenticate(USERNAME_PASSWORD_AUTHENTICATION_TOKEN))
                    .thenThrow(new UsernameNotFoundException("User not found"));
            authUtils.when(() -> AuthUtils.createUser(USER_ID)).thenReturn(INTERNAL_USER);
        }

        @AfterEach
        void tearDown() {
            authUtils.close();

            verify(securityContext).setAuthentication(EXPECTED_AUTHENTICATION);
        }

        @Test
        void shouldAuthenticateByMessage() {
            var message = Message.builder().from(TelegramEventHandlerAuthenticationTest.TELEGRAM_USER).build();

            telegramEventHandlerAuthentication.authenticate(message);
        }

        @Test
        void shouldAuthenticateByCallbackQuery() {
            var callbackQuery = new CallbackQuery();

            callbackQuery.setFrom(TelegramEventHandlerAuthenticationTest.TELEGRAM_USER);

            telegramEventHandlerAuthentication.authenticate(callbackQuery);
        }
    }
}