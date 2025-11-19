package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Functional interface for sending Reddit metadata.
 */
@FunctionalInterface
public interface RedditMetadataSender {
    /**
     * Sends the Reddit metadata.
     *
     * @param metadata   the Reddit post metadata
     * @param hasSpoiler indicates if the content has a spoiler
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException;
}
