package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata;

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
 * Sends Reddit animation metadata as Telegram animations.
 */
@RequiredArgsConstructor
public class RedditAnimationMetadataSender implements RedditMediaMetadataSender {
    private final RedditSendAnimationDTOMapper sendAnimationDTOMapper;
    private final PostSender<CloseableSupplier<SendAnimationDTO>, Message> animationSender;

    @Override
    public void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        animationSender.send(() -> sendAnimationDTOMapper.map(metadata, hasSpoiler));
    }
}
