package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;

/**
 * Implementation of {@link AbstractRedditAnimationSender} for sending Reddit video animations.
 */
public class RedditVideoAnimationSender extends AbstractRedditAnimationSender {
    public RedditVideoAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        super(animationSender);
    }

    @Override
    protected URL getUrl(Link post) {
        return post.preview()
                .redditVideoPreview()
                .fallbackUrl();
    }
}
