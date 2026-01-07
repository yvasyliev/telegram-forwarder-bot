package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendVideoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Sender for Reddit hosted video posts.
 */
@RequiredArgsConstructor
public class RedditHostedVideoSender implements RedditPostSenderStrategy {
    private final RedditSendVideoDTOMapper sendVideoDTOMapper;
    private final PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender;

    @Override
    public boolean canSend(Link post) {
        return Link.PostHint.HOSTED_VIDEO.equals(post.postHint());
    }

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        videoSender.send(() -> sendVideoDTOMapper.map(post));
    }
}
