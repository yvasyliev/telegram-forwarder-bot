package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.Collections;

/**
 * Selects the best photo URL from Reddit link metadata based on dimension constraints.
 */
@RequiredArgsConstructor
public class MetadataPhotoUrlSelector {
    private final int maxDimensionSum;

    /**
     * Finds the best photo URL from the given Reddit link metadata.
     *
     * @param metadata the Reddit link metadata
     * @return the best photo URL
     */
    public URL findBestPhotoUrl(Link.Metadata metadata) {
        return findBestResolution(metadata).url();
    }

    private Link.Resolution findBestResolution(Link.Metadata metadata) {
        var source = metadata.source();

        return source.width() + source.height() > maxDimensionSum
                ? Collections.max(metadata.resolutions())
                : source;
    }
}
