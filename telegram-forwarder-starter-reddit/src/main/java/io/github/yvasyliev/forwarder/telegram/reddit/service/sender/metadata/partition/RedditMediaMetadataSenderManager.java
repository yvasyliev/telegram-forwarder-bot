package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditMediaMetadataSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Manager for sending Reddit media metadata partitions.
 */
@RequiredArgsConstructor
public class RedditMediaMetadataSenderManager implements RedditMetadataPartitionSender {
    private final RedditMediaMetadataSender animationMetadataSender;
    private final RedditMediaMetadataSender photoMetadataSender;

    @Override
    public void send(List<Link.Metadata> metadataPartition, boolean hasSpoiler, String caption)
            throws IOException, TelegramApiException {
        var metadata = metadataPartition.getFirst();

        getSender(metadata).send(metadata, hasSpoiler);
    }

    private RedditMediaMetadataSender getSender(Link.Metadata metadata) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> animationMetadataSender;
            case IMAGE -> photoMetadataSender;
        };
    }
}
