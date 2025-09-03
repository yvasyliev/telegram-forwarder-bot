package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Functional interface for forwarding metadata from Reddit links to Telegram.
 * Implementations should define how to handle the metadata and whether it has a spoiler.
 */
@FunctionalInterface
public interface MetadataForwarder {
    /**
     * Forwards the metadata of a Reddit link to Telegram.
     *
     * @param metadata   the metadata of the Reddit link
     * @param hasSpoiler indicates whether the link has a spoiler
     * @throws IOException          if an I/O error occurs while forwarding
     * @throws TelegramApiException if an error occurs while sending the message to Telegram
     */
    void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException;
}
