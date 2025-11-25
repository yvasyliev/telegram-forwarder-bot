package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;

/**
 * Implementation of {@link AbstractRedditAnimationSender} for sending image animations.
 */
public class RedditImageAnimationSender extends AbstractRedditAnimationSender {
    public RedditImageAnimationSender(PostSender<InputFileDTO, Message> animationSender) {
        super(animationSender);
    }

    @Override
    protected URL getUrl(Link post) {
        return post.preview().images().getFirst().variants().mp4().source().url();
    }
}
