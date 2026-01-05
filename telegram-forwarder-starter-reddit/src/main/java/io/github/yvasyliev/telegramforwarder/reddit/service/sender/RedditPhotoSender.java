package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.SendPhotoDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.core.util.CloseableSupplier;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.mapper.RedditSendPhotoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Sender for Reddit photo posts.
 */
@RequiredArgsConstructor
public class RedditPhotoSender implements RedditPostSender {
    private final RedditSendPhotoDTOMapper sendPhotoDTOMapper;
    private final PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        photoSender.send(() -> sendPhotoDTOMapper.map(post));
    }
}
