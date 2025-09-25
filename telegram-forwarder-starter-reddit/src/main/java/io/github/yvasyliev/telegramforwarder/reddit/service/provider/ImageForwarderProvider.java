package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link Forwarder} for links with the post hint of {@link Link.PostHint#IMAGE}.
 * It distinguishes between static images and animated images (GIFs) based on the link's preview data.
 * If the link does not have the post hint of IMAGE, it returns {@code null}.
 */
@RequiredArgsConstructor
public class ImageForwarderProvider implements ForwarderProvider {
    private final Forwarder imageAnimationForwarder;
    private final Forwarder photoForwarder;

    @Override
    public Forwarder apply(Link link) {
        if (!Link.PostHint.IMAGE.equals(link.postHint())) {
            return null;
        }

        return link.preview().images().getFirst().variants().hasGif() ? imageAnimationForwarder : photoForwarder;
    }
}
