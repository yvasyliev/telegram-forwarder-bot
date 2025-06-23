package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

/**
 * Aspect for handling authentication of Telegram events.
 * It authenticates the user based on the message or callback query received.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class TelegramEventHandlerAuthentication {
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates the user based on the message received.
     * This method is called before handling a message event.
     *
     * @param message the message containing the user information
     */
    @Before("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.handleMessageEvent() && args(message)")
    public void authenticate(Message message) {
        authenticate(message.getFrom());
    }

    /**
     * Authenticates the user based on the callback query received.
     * This method is called before handling a callback query event.
     *
     * @param query the callback query containing the user information
     */
    @Before("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.handleCallbackQueryEvent() && args(query)")
    public void authenticate(CallbackQuery query) {
        authenticate(query.getFrom());
    }

    private void authenticate(User user) {
        var authentication = authenticate(user.getId());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication authenticate(Long userId) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userId,
                    StringUtils.EMPTY,
                    List.of()
            ));
        } catch (UsernameNotFoundException e) {
            var user = AuthUtils.createUser(userId);
            return new AnonymousAuthenticationToken(user.getUsername(), user, user.getAuthorities());
        }
    }
}
