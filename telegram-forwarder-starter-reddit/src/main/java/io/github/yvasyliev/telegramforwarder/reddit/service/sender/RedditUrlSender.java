package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation of {@link RedditPostSender} for sending Reddit posts as URLs.
 */
@RequiredArgsConstructor
public class RedditUrlSender implements RedditPostSender {
    private final PostSender<URL, Message> urlSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        urlSender.send(post.url(), post.title());
    }
}
