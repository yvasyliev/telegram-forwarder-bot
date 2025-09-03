package io.github.yvasyliev.telegramforwarder.bot;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

/**
 * Telegram bot that forwards messages and handles updates.
 */
@Slf4j
@RequiredArgsConstructor
public class TelegramForwarderBot implements SpringLongPollingBot {
    @Getter private final String botToken;
    @Getter private final LongPollingUpdateConsumer updatesConsumer;
    @Getter private final User me;
    private final Long adminId;
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

    /**
     * Initializes the bot by sending a message to the admin chat indicating that the bot is running.
     *
     * @throws TelegramApiException if an error occurs while sending the message
     */
    @PostConstruct
    public void init() throws TelegramApiException {
        var context = new Context();
        context.setVariable("botName", me.getFirstName());

        var sendMessage = SendMessage.builder()
                .chatId(adminId)
                .text(templateEngine.process("running", context))
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
    }

    /**
     * Shuts down the bot by sending a shutdown message to the admin chat.
     *
     * @throws TelegramApiException if an error occurs while sending the shutdown message
     */
    @PreDestroy
    public void shutdown() throws TelegramApiException {
        var sendMessage = SendMessage.builder()
                .chatId(adminId)
                .text(templateEngine.process("shutdown", new Context()))
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
    }
}
