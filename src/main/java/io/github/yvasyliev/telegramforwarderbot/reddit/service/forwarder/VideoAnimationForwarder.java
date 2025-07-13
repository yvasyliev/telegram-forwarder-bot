package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.AnimationSender;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Forwards video animations from Reddit links.
 */
@Service
public class VideoAnimationForwarder extends AbstractAnimationForwarder {
    public VideoAnimationForwarder(AnimationSender animationSender) {
        super(animationSender);
    }

    @Override
    protected URL getUrl(Link link) {
        return link.preview()
                .redditVideoPreview()
                .fallbackUrl();
    }
}
