package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating Reddit post senders for hosted videos.
 */
@RequiredArgsConstructor
public class RedditHostedVideoSenderFactory implements RedditPostSenderFactory {
    private final RedditPostSender redditVideoSender;

    @Override
    public RedditPostSender getRedditPostSender(Link post) {
        return Link.PostHint.HOSTED_VIDEO.equals(post.postHint()) ? redditVideoSender : null;
    }
}
