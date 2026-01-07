package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.security.authentication.TelegramAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Aspect for handling authentication of Telegram events.
 * It authenticates the user based on the message or callback query received.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class TelegramEventHandlerAuthentication {
    private final TelegramAuthenticationManager authenticationManager;

    /**
     * Authenticates the user based on the message received.
     * This method is called before handling a message event.
     *
     * @param message the message containing the user information
     */
    @Before("io.github.yvasyliev.forwarder.telegram.bot.util.Pointcuts.handleMessageEvent() && args(message)")
    public void authenticate(Message message) {
        authenticate(message.getFrom());
    }

    /**
     * Authenticates the user based on the callback query received.
     * This method is called before handling a callback query event.
     *
     * @param query the callback query containing the user information
     */
    @Before("io.github.yvasyliev.forwarder.telegram.bot.util.Pointcuts.handleCallbackQueryEvent() && args(query)")
    public void authenticate(CallbackQuery query) {
        authenticate(query.getFrom());
    }

    private void authenticate(User user) {
        var authentication = authenticationManager.authenticate(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
