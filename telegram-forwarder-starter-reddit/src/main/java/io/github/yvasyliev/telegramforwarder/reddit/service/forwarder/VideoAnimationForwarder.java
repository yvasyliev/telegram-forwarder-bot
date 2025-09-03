package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;

/**
 * Forwards video animations from Reddit links.
 */
public class VideoAnimationForwarder extends AbstractAnimationForwarder {
    public VideoAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        super(animationSender);
    }

    @Override
    protected URL getUrl(Link link) {
        return link.preview()
                .redditVideoPreview()
                .fallbackUrl();
    }
}
