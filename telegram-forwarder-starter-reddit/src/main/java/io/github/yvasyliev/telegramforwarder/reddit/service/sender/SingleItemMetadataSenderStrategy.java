package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for sending a single metadata item.
 */
@RequiredArgsConstructor
public class SingleItemMetadataSenderStrategy implements MetadataPartitionSenderStrategy {
    private final RedditMetadataSenderResolver redditMetadataSenderResolver;

    @Override
    public void send(
            List<Link.Metadata> metadataPartition,
            boolean hasSpoiler,
            String caption
    ) throws IOException, TelegramApiException {
        var metadata = metadataPartition.getFirst();

        redditMetadataSenderResolver.resolve(metadata).send(metadata, hasSpoiler);
    }
}
