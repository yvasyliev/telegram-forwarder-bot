package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * A functional interface for sending a partition of Reddit metadata.
 */
@FunctionalInterface
public interface RedditMetadataPartitionSender {
    /**
     * Sends a partition of Reddit metadata.
     *
     * @param metadataPartition the list of Reddit metadata to send
     * @param hasSpoiler        indicates if the content has a spoiler
     * @param caption           the caption to accompany the sent content
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(List<Link.Metadata> metadataPartition, boolean hasSpoiler, String caption)
            throws IOException, TelegramApiException;
}
