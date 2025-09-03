package io.github.yvasyliev.telegramforwarder.bot.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class AuthUtilsTest {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    private static final long USER_ID = 123;
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testIsAdmin = () -> Stream.of(
            arguments(ROLE_ADMIN, true),
            arguments(ROLE_USER, false)
    );

    @ParameterizedTest
    @FieldSource
    void testIsAdmin(String role, boolean expected) {
        var securityContext = mock(SecurityContext.class);
        var authentication = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        doReturn(AuthorityUtils.createAuthorityList(role)).when(authentication).getAuthorities();

        try (var securityContextHolder = mockStatic(SecurityContextHolder.class)) {
            securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            var actual = AuthUtils.isAdmin();

            assertEquals(expected, actual);
        }
    }

    @Test
    void testCreateAdmin() {
        var expected = new User(
                String.valueOf(USER_ID),
                AuthUtils.DEFAULT_PASSWORD,
                AuthorityUtils.createAuthorityList(ROLE_ADMIN)
        );

        var actual = AuthUtils.createAdmin(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateUser() {
        var expected = new User(
                String.valueOf(USER_ID),
                AuthUtils.DEFAULT_PASSWORD,
                AuthorityUtils.createAuthorityList(ROLE_USER)
        );

        var actual = AuthUtils.createUser(USER_ID);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateUserWithAuthorities() {
        var authorities = AuthorityUtils.createAuthorityList(ROLE_ADMIN, ROLE_USER);
        var expected = new User(String.valueOf(USER_ID), AuthUtils.DEFAULT_PASSWORD, authorities);

        var actual = AuthUtils.createUser(USER_ID, authorities);

        assertEquals(expected, actual);
    }
}
