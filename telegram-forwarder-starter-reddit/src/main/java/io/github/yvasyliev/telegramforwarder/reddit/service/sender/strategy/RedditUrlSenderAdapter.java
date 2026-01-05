package io.github.yvasyliev.telegramforwarder.reddit.service.sender.strategy;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
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
