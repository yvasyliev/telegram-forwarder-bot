package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating Reddit post senders for URLs.
 */
@RequiredArgsConstructor
public class RedditUrlSenderFactory implements RedditPostSenderFactory {
    private final RedditPostSender redditUrlSender;

    @Override
    public RedditPostSender getRedditPostSender(Link post) {
        return Link.PostHint.LINK.equals(post.postHint()) ? redditUrlSender : null;
    }
}
