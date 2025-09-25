package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import lombok.RequiredArgsConstructor;

/**
 * Provides a {@link LinkForwarder} for links with the post hint of {@link Link.PostHint#HOSTED_VIDEO}.
 * If the link does not have this post hint, it returns {@code null}.
 */
@RequiredArgsConstructor
public class HostedVideoForwarderProvider implements LinkForwarderProvider {
    private final LinkForwarder videoForwarder;

    @Override
    public LinkForwarder apply(Link link) {
        return Link.PostHint.HOSTED_VIDEO.equals(link.postHint()) ? videoForwarder : null;
    }
}
