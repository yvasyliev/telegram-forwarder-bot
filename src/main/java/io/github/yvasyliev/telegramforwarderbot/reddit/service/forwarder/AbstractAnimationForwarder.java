package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.AnimationSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
public abstract class AbstractAnimationForwarder implements Forwarder {
    private final AnimationSender animationSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var url = getUrl(link);
        var animation = InputFileDTO.fromUrl(url, link.isNsfw());

        animationSender.sendAnimation(animation, link.title());
    }

    protected abstract URL getUrl(Link link);
}
