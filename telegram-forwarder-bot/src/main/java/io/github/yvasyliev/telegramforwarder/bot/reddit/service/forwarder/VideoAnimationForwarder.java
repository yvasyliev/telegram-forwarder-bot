package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;

/**
 * Forwards video animations from Reddit links.
 */
@Service
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
