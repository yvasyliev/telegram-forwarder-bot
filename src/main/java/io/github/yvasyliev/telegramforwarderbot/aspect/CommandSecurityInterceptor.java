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

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CommandSecurityInterceptor {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

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
