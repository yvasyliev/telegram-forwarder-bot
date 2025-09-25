package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link LinkForwarder} for links with the post hint of {@link Link.PostHint#RICH_VIDEO}.
 * If the link does not have this post hint, it returns {@code null}.
 *
 * <p>
 * If the rich video is a GIF, it uses the {@code videoAnimationForwarder}; otherwise, it uses the {@code
 * linkForwarder}.
 */
@RequiredArgsConstructor
public class RichVideoForwarderProvider implements LinkForwarderProvider {
    private final LinkForwarder videoAnimationForwarder;
    private final LinkForwarder linkForwarder;

    @Override
    public LinkForwarder apply(Link link) {
        if (!Link.PostHint.RICH_VIDEO.equals(link.postHint())) {
            return null;
        }

        var redditVideo = link.preview().redditVideoPreview();
        return redditVideo != null && redditVideo.isGif() ? videoAnimationForwarder : linkForwarder;
    }
}
