package io.github.yvasyliev.telegramforwarderbot.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
@Slf4j
public class TelegramBotAppender extends AppenderBase<ILoggingEvent> {
    private static final String FORBIDDEN_LOG_MESSAGE = "Send /start command to the bot in Telegram to allow it to " +
            "send log messages to you, or provide logging.telegram-bot-appender.enabled=false property to disable " +
            "bot appender.";
    private final TelegramClient telegramClient;
    private final TelegramTemplateEngine templateEngine;
    private final long adminId;
    private final Layout<ILoggingEvent> patternLayout;

    @Override
    public void append(ILoggingEvent event) {
        var context = new Context();
        context.setVariable("text", patternLayout.doLayout(event));

        var sendMessage = SendMessage.builder()
                .chatId(adminId)
                .text(templateEngine.process("error", context))
                .parseMode(ParseMode.HTML)
                .build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException ex) {
            if (ex instanceof TelegramApiRequestException e && e.getErrorCode().equals(HttpStatus.FORBIDDEN.value())) {
                log.warn(FORBIDDEN_LOG_MESSAGE);
            } else {
                log.error("{} failed to send error message.", getName(), ex);
                addError(getName() + " failed to send error message.", ex);
            }
        }
    }
}
