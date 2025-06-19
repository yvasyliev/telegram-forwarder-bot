package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.Supplier;

/**
 * Aspect that intercepts command execution and handles security checks.
 * If access is denied, it sends an unauthorized action message to the user.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandSecurityInterceptor {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Intercepts method calls that handle message events and callback queries.
     * If an {@link AccessDeniedException} is thrown, it sends an unauthorized action message.
     *
     * @param pjp     the proceeding join point
     * @param message the message object for message events
     * @return the result of the intercepted method or an unauthorized action message
     * @throws Throwable if any error occurs during method execution
     */
    @Around("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.handleMessageEvent() && args(message)")
    public Object intercept(ProceedingJoinPoint pjp, Message message) throws Throwable {
        return intercept(
                pjp,
                () -> SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(telegramProperties.unauthorizedActionText())
                        .build()
        );
    }

    /**
     * Intercepts method calls that handle callback query events.
     * If an {@link AccessDeniedException} is thrown, it sends an unauthorized action response.
     *
     * @param pjp   the proceeding join point
     * @param query the callback query object for callback query events
     * @return the result of the intercepted method or an unauthorized action response
     * @throws Throwable if any error occurs during method execution
     */
    @Around("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.handleCallbackQueryEvent() && args(query)")
    public Object intercept(ProceedingJoinPoint pjp, CallbackQuery query) throws Throwable {
        return intercept(
                pjp,
                () -> AnswerCallbackQuery.builder()
                        .callbackQueryId(query.getId())
                        .text(telegramProperties.unauthorizedActionText())
                        .build()
        );
    }

    private Object intercept(ProceedingJoinPoint pjp, Supplier<BotApiMethod<?>> methodSupplier) throws Throwable {
        try {
            return pjp.proceed();
        } catch (AccessDeniedException e) {
            return telegramClient.execute(methodSupplier.get());
        }
    }
}
