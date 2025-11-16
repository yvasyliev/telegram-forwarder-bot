package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link LinkForwarder} for links with the post hint of {@link Link.PostHint#IMAGE}.
 * It distinguishes between static images and animated images (GIFs) based on the link's preview data.
 * If the link does not have the post hint of IMAGE, it returns {@code null}.
 */
@RequiredArgsConstructor
public class ImageForwarderProvider implements LinkForwarderProvider {
    private final LinkForwarder imageAnimationForwarder;
    private final LinkForwarder photoForwarder;

    @Override
    public LinkForwarder apply(Link link) {
        if (!Link.PostHint.IMAGE.equals(link.postHint())) {
            return null;
        }

        return link.preview().images().getFirst().variants().hasGif() ? imageAnimationForwarder : photoForwarder;
    }
}
