package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link LinkForwarder} for links that contain gallery data.
 * If the link does not have gallery data, it returns {@code null}.
 */
@RequiredArgsConstructor
public class MediaGroupForwarderProvider implements LinkForwarderProvider {
    private final LinkForwarder mediaGroupForwarder;

    @Override
    public LinkForwarder apply(Link link) {
        return link.hasGalleryData() ? mediaGroupForwarder : null;
    }
}
