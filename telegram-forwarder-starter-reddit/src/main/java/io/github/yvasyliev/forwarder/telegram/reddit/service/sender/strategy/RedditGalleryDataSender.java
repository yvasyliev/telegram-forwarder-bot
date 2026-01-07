package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition.RedditMetadataPartitionSender;
import io.github.yvasyliev.forwarder.telegram.reddit.util.GalleryMetadataPartitioner;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for sending Reddit posts that contain gallery data.
 */
@RequiredArgsConstructor
public class RedditGalleryDataSender implements RedditPostSenderStrategy {
    private final GalleryMetadataPartitioner galleryMetadataPartitioner;
    private final RedditMetadataPartitionSender mediaMetadataSender;
    private final RedditMetadataPartitionSender mediaGroupMetadataSender;

    @Override
    public boolean canSend(Link post) {
        return post.hasGalleryData();
    }

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var caption = post.title();
        var hasSpoiler = post.isNsfw();

        for (var metadataPartition : galleryMetadataPartitioner.partition(post)) {
            getSender(metadataPartition).send(metadataPartition, hasSpoiler, caption);

            caption = null;
        }
    }

    private RedditMetadataPartitionSender getSender(List<Link.Metadata> partition) {
        return NumberUtils.INTEGER_ONE.equals(partition.size()) ? mediaMetadataSender : mediaGroupMetadataSender;
    }
}
