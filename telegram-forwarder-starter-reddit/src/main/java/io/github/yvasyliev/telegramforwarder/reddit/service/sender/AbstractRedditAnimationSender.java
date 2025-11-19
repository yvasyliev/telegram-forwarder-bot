package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

/**
 * Abstract class for sending Reddit animation posts.
 */
@RequiredArgsConstructor
public abstract class AbstractRedditAnimationSender implements RedditPostSender {
    private final PostSender<InputFileDTO, Message> animationSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var url = getUrl(post);
        var animation = InputFileDTO.fromUrl(url, post.isNsfw());

        animationSender.send(animation, post.title());
    }

    protected abstract URL getUrl(Link link);
}
