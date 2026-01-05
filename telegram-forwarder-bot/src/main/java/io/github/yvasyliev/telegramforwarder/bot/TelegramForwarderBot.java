package io.github.yvasyliev.telegramforwarder.bot;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramBotProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

/**
 * Telegram bot implementation for forwarding messages.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramForwarderBot implements SpringLongPollingBot {
    @Delegate
    private final TelegramBotProperties botProperties;

    @Getter(onMethod_ = @Override)
    private final LongPollingUpdateConsumer updatesConsumer;
}
