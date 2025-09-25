package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link Forwarder} for links that contain gallery data.
 * If the link does not have gallery data, it returns {@code null}.
 */
@RequiredArgsConstructor
public class MediaGroupForwarderProvider implements ForwarderProvider {
    private final Forwarder mediaGroupForwarder;

    @Override
    public Forwarder apply(Link link) {
        return link.hasGalleryData() ? mediaGroupForwarder : null;
    }
}
