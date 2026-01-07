package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;

/**
 * Strategy interface for sending Reddit posts based on specific criteria.
 */
public interface RedditPostSenderStrategy extends RedditPostSender {
    /**
     * Determines if this strategy can send the given Reddit post.
     *
     * @param post the Reddit post to evaluate
     * @return {@code true} if this strategy can send the post, {@code false} otherwise
     */
    boolean canSend(Link post);
}
