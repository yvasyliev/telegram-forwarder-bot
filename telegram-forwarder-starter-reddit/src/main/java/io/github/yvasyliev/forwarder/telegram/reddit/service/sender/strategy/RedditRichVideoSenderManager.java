package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Manager for sending Reddit rich video posts.
 */
@Service
@RequiredArgsConstructor
public class RedditRichVideoSenderManager implements RedditPostSenderStrategy {
    private final RedditPostSender redditAnimationSender;
    private final RedditPostSender redditUrlSender;

    @Override
    public boolean canSend(Link post) {
        return Link.PostHint.RICH_VIDEO.equals(post.postHint());
    }

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        getSender(post).send(post);
    }

    private RedditPostSender getSender(Link post) {
        return post.isRedditMediaDomain() && isGif(post) ? redditAnimationSender : redditUrlSender;
    }

    private boolean isGif(Link post) {
        var redditVideo = post.preview().redditVideoPreview();

        return redditVideo != null && redditVideo.isGif();
    }
}
