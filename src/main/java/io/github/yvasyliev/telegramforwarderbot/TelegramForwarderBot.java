package io.github.yvasyliev.telegramforwarderbot;

import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
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

@Slf4j
@RequiredArgsConstructor
public class TelegramForwarderBot implements SpringLongPollingBot {
    @Getter private final String botToken;
    @Getter private final LongPollingUpdateConsumer updatesConsumer;
    @Getter private final User me;
    private final Long adminId;
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

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

    @PreDestroy
    public void shutdown() throws TelegramApiException {
        var sendMessage = SendMessage.builder()
                .chatId(adminId)
                .text(templateEngine.process("shutdown"))
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
    }
}
