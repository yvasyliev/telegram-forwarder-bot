package io.github.yvasyliev.telegramforwarder.bot.security.authentication;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Manages authentication for Telegram users.
 */
@RequiredArgsConstructor
public class TelegramAuthenticationManager implements AuthenticationManager {
    @Delegate
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a Telegram user. If the user is not found, returns an anonymous authentication token.
     *
     * @param user the Telegram user to authenticate
     * @return the authentication token
     */
    public Authentication authenticate(User user) {
        try {
            return authenticate(new TelegramAuthentication(user));
        } catch (UsernameNotFoundException _) {
            return TelegramAnonymousAuthentication.create(user);
        }
    }
}
