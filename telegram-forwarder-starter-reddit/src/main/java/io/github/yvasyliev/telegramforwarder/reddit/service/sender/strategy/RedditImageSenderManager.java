package io.github.yvasyliev.telegramforwarder.reddit.service.sender.strategy;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Manager for sending Reddit image posts.
 */
@RequiredArgsConstructor
public class RedditImageSenderManager implements RedditPostSenderStrategy {
    private final RedditPostSender redditAnimationSender;
    private final RedditPostSender redditPhotoSender;

    @Override
    public boolean canSend(Link post) {
        return Link.PostHint.IMAGE.equals(post.postHint());
    }

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        getSender(post).send(post);
    }

    private RedditPostSender getSender(Link post) {
        return post.preview().images().getFirst().variants().hasGif() ? redditAnimationSender : redditPhotoSender;
    }
}
