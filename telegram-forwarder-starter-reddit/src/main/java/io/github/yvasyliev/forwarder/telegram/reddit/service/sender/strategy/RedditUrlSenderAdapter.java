package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * Adapter for sending Reddit posts that are links (URLs).
 */
@RequiredArgsConstructor
public class RedditUrlSenderAdapter implements RedditPostSenderStrategy {
    @Delegate
    private final RedditPostSender urlSender;

    @Override
    public boolean canSend(Link post) {
        return Link.PostHint.LINK.equals(post.postHint());
    }
}
