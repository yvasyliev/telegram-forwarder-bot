package io.github.yvasyliev.forwarder.telegram.reddit.util;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;

/**
 * Selects the best photo URL from Reddit link metadata based on dimension constraints.
 */
@Component
@RequiredArgsConstructor
public class RedditMetadataPhotoUrlSelector {
    private final TelegramMediaProperties mediaProperties;

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

        return source.width() + source.height() > mediaProperties.photoMaxDimensionSum()
                ? Collections.max(metadata.resolutions())
                : source;
    }
}
