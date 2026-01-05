package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * A functional interface for sending Reddit posts to Telegram.
 */
@FunctionalInterface
public interface RedditPostSender {
    /**
     * Sends a Reddit post to Telegram.
     *
     * @param post the Reddit post to be sent
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(Link post) throws IOException, TelegramApiException;
}
