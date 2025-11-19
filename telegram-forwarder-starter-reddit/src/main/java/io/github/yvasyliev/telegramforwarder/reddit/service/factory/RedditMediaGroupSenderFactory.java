package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating Reddit post senders for media groups.
 */
@RequiredArgsConstructor
public class RedditMediaGroupSenderFactory implements RedditPostSenderFactory {
    private final RedditPostSender redditMediaGroupSender;

    @Override
    public RedditPostSender getRedditPostSender(Link post) {
        return post.hasGalleryData() ? redditMediaGroupSender : null;
    }
}
