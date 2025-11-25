package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;

/**
 * Factory for creating Reddit post senders for images.
 */
@RequiredArgsConstructor
public class RedditImageSenderFactory implements RedditPostSenderFactory {
    private final RedditPostSender redditImageAnimationSender;
    private final RedditPostSender redditPhotoSender;

    @Override
    public RedditPostSender getRedditPostSender(Link post) {
        if (!Link.PostHint.IMAGE.equals(post.postHint())) {
            return null;
        }

        return post.preview().images().getFirst().variants().hasGif() ? redditImageAnimationSender : redditPhotoSender;
    }
}
