package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Service for sending Reddit posts.
 */
@FunctionalInterface
public interface RedditPostSender {
    /**
     * Sends the given Reddit post.
     *
     * @param post the Reddit post
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(Link post) throws IOException, TelegramApiException;
}
