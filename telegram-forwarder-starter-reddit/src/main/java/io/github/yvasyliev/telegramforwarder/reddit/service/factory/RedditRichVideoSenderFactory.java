package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating Reddit post senders for rich videos.
 */
@RequiredArgsConstructor
public class RedditRichVideoSenderFactory implements RedditPostSenderFactory {
    private final RedditPostSender redditVideoAnimationSender;
    private final RedditPostSender redditUrlSender;

    @Override
    public RedditPostSender getRedditPostSender(Link post) {
        if (!Link.PostHint.RICH_VIDEO.equals(post.postHint())) {
            return null;
        }

        var redditVideo = post.preview().redditVideoPreview();
        return redditVideo != null && redditVideo.isGif() ? redditVideoAnimationSender : redditUrlSender;
    }
}
