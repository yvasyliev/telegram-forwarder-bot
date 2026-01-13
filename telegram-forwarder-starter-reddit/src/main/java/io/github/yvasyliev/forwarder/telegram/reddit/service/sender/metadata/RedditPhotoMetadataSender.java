package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendPhotoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Sends photo metadata from Reddit posts to Telegram.
 */
@RequiredArgsConstructor
public class RedditPhotoMetadataSender implements RedditMediaMetadataSender {
    private final RedditSendPhotoDTOMapper sendPhotoDTOMapper;
    private final PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender;

    @Override
    public void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        photoSender.send(() -> sendPhotoDTOMapper.map(metadata, hasSpoiler));
    }
}
