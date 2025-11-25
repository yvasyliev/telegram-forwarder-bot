package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.GalleryMetadataPartitioner;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implementation of {@link RedditPostSender} for sending media group posts.
 */
@RequiredArgsConstructor
public class RedditMediaGroupSender implements RedditPostSender {
    private final GalleryMetadataPartitioner galleryMetadataPartitioner;
    private final MetadataPartitionSenderStrategyResolver metadataPartitionSenderStrategyResolver;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var hasSpoiler = post.isNsfw();
        var caption = post.title();

        for (var metadataPartition : galleryMetadataPartitioner.partition(post)) {
            metadataPartitionSenderStrategyResolver.resolve(metadataPartition).send(
                    metadataPartition,
                    hasSpoiler,
                    caption
            );

            caption = null;
        }
    }
}
