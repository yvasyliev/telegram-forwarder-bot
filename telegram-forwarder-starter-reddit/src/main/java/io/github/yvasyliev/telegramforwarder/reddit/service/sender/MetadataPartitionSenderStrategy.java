package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for sending a partition of metadata items.
 */
@FunctionalInterface
public interface MetadataPartitionSenderStrategy {
    /**
     * Sends the given partition of metadata items.
     *
     * @param metadataPartition the list of metadata items to send
     * @param hasSpoiler        indicates if the items have spoilers
     * @param caption           the caption to include with the sent items
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    void send(
            List<Link.Metadata> metadataPartition,
            boolean hasSpoiler,
            String caption
    ) throws IOException, TelegramApiException;
}
