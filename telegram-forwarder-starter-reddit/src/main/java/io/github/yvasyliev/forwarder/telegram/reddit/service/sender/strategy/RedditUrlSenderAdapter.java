package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

/**
 * Adapter for sending Reddit posts that are links (URLs).
 */
@Service
@RequiredArgsConstructor
public class RedditUrlSenderAdapter implements RedditPostSenderStrategy {
    @Delegate
    private final RedditPostSender redditUrlSender;

    @Override
    public boolean canSend(Link post) {
        return Link.PostHint.LINK.equals(post.postHint());
    }
}
