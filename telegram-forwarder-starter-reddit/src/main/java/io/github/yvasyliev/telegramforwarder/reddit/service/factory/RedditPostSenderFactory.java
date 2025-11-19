package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;

/**
 * Factory for creating Reddit post senders.
 */
@FunctionalInterface
public interface RedditPostSenderFactory {
    /**
     * Gets the appropriate Reddit post sender for the given post.
     *
     * @param post the Reddit post
     * @return the Reddit post sender, or {@code null} if none is applicable
     */
    RedditPostSender getRedditPostSender(Link post);
}
