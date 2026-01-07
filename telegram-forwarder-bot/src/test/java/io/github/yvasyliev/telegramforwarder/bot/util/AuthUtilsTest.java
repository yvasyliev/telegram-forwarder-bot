package io.github.yvasyliev.telegramforwarder.bot.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthUtilsTest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testIsAdmin = () -> {
        var authentication1 = mock(Authentication.class);
        var authentication2 = mock(Authentication.class);
        var authentication3 = mock(Authentication.class);

        doReturn(AuthorityUtils.createAuthorityList()).when(authentication1).getAuthorities();
        doReturn(AuthorityUtils.createAuthorityList("ROLE_USER")).when(authentication2).getAuthorities();
        doReturn(AuthorityUtils.createAuthorityList(AuthUtils.ROLE_ADMIN)).when(authentication3).getAuthorities();

        return Stream.of(
                arguments(null, false),
                arguments(authentication1, false),
                arguments(authentication2, false),
                arguments(authentication3, true)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testIsAdmin(Authentication authentication, boolean expected) {
        var securityContext = mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        var actual = AuthUtils.isAdmin();

        assertEquals(expected, actual);
    }
}
