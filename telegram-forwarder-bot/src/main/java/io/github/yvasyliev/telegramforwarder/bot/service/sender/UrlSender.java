package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

import java.net.URL;

/**
 * Service for sending URLs to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class UrlSender implements PostSender<URL, Message> {
    private static final String URL = "url";
    private final TelegramProperties telegramProperties;
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

    /**
     * Sends a message containing a URL to the admin chat.
     *
     * @param url     the URL to send
     * @param message the message to accompany the URL
     * @return the sent message
     * @throws TelegramApiException if an error occurs while sending the message
     */
    @Override
    public Message send(URL url, String message) throws TelegramApiException {
        var context = new Context();
        context.setVariable("text", message);
        context.setVariable(URL, url);

        var sendMessage = SendMessage.builder()
                .chatId(telegramProperties.adminId())
                .text(templateEngine.process(URL, context))
                .parseMode(ParseMode.HTML)
                .build();
        return telegramClient.execute(sendMessage);
    }
}
