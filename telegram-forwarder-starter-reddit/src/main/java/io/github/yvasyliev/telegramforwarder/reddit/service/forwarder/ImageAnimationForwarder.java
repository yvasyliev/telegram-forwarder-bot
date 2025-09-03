package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;

/**
 * Forwards image animations from Reddit links.
 */
public class ImageAnimationForwarder extends AbstractAnimationForwarder {
    public ImageAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
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
