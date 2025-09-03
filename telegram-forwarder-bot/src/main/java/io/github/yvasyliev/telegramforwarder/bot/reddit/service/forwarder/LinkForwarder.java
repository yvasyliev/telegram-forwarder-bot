package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

/**
 * Forwards links from Reddit to Telegram.
 */
@Service
@RequiredArgsConstructor
public class LinkForwarder implements Forwarder {
    private final PostSender<URL, Message> urlSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        urlSender.send(link.url(), link.title());
    }
}
