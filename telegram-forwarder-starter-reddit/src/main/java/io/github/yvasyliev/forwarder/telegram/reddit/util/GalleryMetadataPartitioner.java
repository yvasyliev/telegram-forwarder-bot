package io.github.yvasyliev.forwarder.telegram.reddit.util;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Partitions gallery metadata items from a Reddit post into smaller lists of a specified size.
 */
@Component
@RequiredArgsConstructor
public class GalleryMetadataPartitioner {
    private final TelegramMediaProperties mediaProperties;

    /**
     * Partitions the gallery metadata items of the given Reddit post.
     *
     * @param post the Reddit post containing gallery data
     * @return a list of lists, where each inner list contains a partition of metadata items
     */
    public List<List<Link.Metadata>> partition(Link post) {
        var metadataList = post.galleryData()
                .items()
                .stream()
                .map(item -> post.mediaMetadata().get(item.mediaId()))
                .toList();

        return ListUtils.partition(metadataList, mediaProperties.groupMaxSize());
    }
}
