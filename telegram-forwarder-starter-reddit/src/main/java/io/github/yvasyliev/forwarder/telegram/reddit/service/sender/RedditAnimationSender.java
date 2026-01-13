package io.github.yvasyliev.forwarder.telegram.reddit.service.sender;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendAnimationDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Sender for Reddit animation posts.
 */
@RequiredArgsConstructor
public class RedditAnimationSender implements RedditPostSender {
    private final RedditSendAnimationDTOMapper sendAnimationDTOMapper;
    private final PostSender<CloseableSupplier<SendAnimationDTO>, Message> animationSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        animationSender.send(() -> sendAnimationDTOMapper.map(post));
    }
}
