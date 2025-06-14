package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.AnimationSender;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class ImageAnimationForwarder extends AbstractAnimationForwarder {
    public ImageAnimationForwarder(AnimationSender animationSender) {
        super(animationSender);
    }

    @Override
    protected URL getUrl(Link link) {
        return link.preview()
                .images()
                .getFirst()
                .variants()
                .mp4()
                .source()
                .url();
    }
}
