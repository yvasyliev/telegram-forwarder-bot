package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class UrlSender {
    private final TelegramProperties telegramProperties;
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

    public Message sendUrl(URL url, String message) throws TelegramApiException {
        var context = new Context();
        context.setVariable("text", message);
        context.setVariable("url", url);

        var sendMessage = SendMessage.builder()
                .chatId(telegramProperties.adminId())
                .text(templateEngine.process("url", context))
                .parseMode(ParseMode.HTML)
                .build();
        return telegramClient.execute(sendMessage);
    }
}
