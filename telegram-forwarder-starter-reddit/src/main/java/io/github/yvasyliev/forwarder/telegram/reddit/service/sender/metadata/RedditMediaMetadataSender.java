package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * A functional interface for sending Reddit media metadata.
 */
@FunctionalInterface
public interface RedditMediaMetadataSender {
    /**
     * Sends the given Reddit media metadata.
     *
     * @param metadata   the Reddit media metadata to send
     * @param hasSpoiler indicates if the media has a spoiler
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException;
}
