package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

/**
 * Abstract class for forwarding animations.
 */
@RequiredArgsConstructor
public abstract class AbstractAnimationForwarder implements Forwarder {
    private final PostSender<InputFileDTO, Message> animationSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var url = getUrl(link);
        var animation = InputFileDTO.fromUrl(url, link.isNsfw());

        animationSender.send(animation, link.title());
    }

    protected abstract URL getUrl(Link link);
}
