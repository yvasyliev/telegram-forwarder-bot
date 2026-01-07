package io.github.yvasyliev.forwarder.telegram.bot.security.authentication;

import io.github.yvasyliev.forwarder.telegram.bot.security.core.TelegramUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

/**
 * Represents an anonymous authentication token for a Telegram user.
 */
public final class TelegramAnonymousAuthentication extends AnonymousAuthenticationToken {
    /**
     * Constructs a new {@link TelegramAnonymousAuthentication} for the given Telegram user.
     *
     * @param user the Telegram user
     */
    private TelegramAnonymousAuthentication(TelegramUser user) {
        super(user.getUsername(), Objects.requireNonNull(user.getPassword()), user.getAuthorities());
    }

    /**
     * Creates a new {@link TelegramAnonymousAuthentication} for the given Telegram user.
     *
     * @param user the Telegram user
     * @return the anonymous authentication token
     */
    public static TelegramAnonymousAuthentication create(User user) {
        return new TelegramAnonymousAuthentication(TelegramUser.createUser(user.getId()));
    }
}
