package io.github.yvasyliev.forwarder.telegram.reddit.service.sender;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendUrlDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Sender for Reddit URL posts.
 */
@RequiredArgsConstructor
public class RedditUrlSender implements RedditPostSender {
    private final RedditSendUrlDTOMapper sendUrlDTOMapper;
    private final PostSender<SendUrlDTO, Message> urlSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        urlSender.send(sendUrlDTOMapper.map(post));
    }
}
