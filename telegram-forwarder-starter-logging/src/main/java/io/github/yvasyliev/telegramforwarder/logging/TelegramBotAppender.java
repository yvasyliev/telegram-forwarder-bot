package io.github.yvasyliev.telegramforwarder.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.logging.mapper.LoggingSendMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.HttpURLConnection;

/**
 * Appender that sends log messages to a Telegram chat.
 */
@RequiredArgsConstructor
@Slf4j
public class TelegramBotAppender extends AppenderBase<ILoggingEvent> {
    private static final String FORBIDDEN_LOG_MESSAGE = "Send /start command to the bot in Telegram to allow it to "
            + "send log messages to you, or provide logging.telegram-bot-appender.enabled=false property to disable "
            + "bot appender.";
    private final TelegramAdminProperties adminProperties;
    private final LoggingSendMessageMapper sendMessageMapper;
    private final TelegramClient telegramClient;

    @Override
    public void append(ILoggingEvent event) {
        var sendMessage = sendMessageMapper.map(adminProperties, event);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("{} failed to send error message.", getName(), e);
            addError(getName() + " failed to send error message.", e);
        }
    }

    private void execute(SendMessage sendMessage) throws TelegramApiException {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiRequestException e) {
            if (ObjectUtils.notEqual(HttpURLConnection.HTTP_FORBIDDEN, e.getErrorCode())) {
                throw e;
            }

            log.warn(FORBIDDEN_LOG_MESSAGE);
        }
    }
}
