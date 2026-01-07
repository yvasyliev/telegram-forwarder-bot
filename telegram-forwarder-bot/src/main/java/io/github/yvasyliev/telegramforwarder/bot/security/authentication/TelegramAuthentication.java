package io.github.yvasyliev.telegramforwarder.bot.security.authentication;

import io.github.yvasyliev.telegramforwarder.bot.util.AuthUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Represents an authentication token for a Telegram user.
 */
public class TelegramAuthentication extends UsernamePasswordAuthenticationToken {
    /**
     * Constructs a new {@link TelegramAuthentication} for the given Telegram user.
     *
     * @param user the Telegram user
     */
    public TelegramAuthentication(User user) {
        super(user.getId(), AuthUtils.DUMMY_PASSWORD);
    }
}
