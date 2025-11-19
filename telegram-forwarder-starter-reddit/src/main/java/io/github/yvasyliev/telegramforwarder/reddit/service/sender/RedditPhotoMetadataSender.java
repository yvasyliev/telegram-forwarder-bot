package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.PhotoUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implementation of {@link RedditMetadataSender} for sending photo metadata.
 */
@RequiredArgsConstructor
public class RedditPhotoMetadataSender implements RedditMetadataSender {
    private final TelegramMediaProperties mediaProperties;
    private final PostSender<InputFileDTO, Message> photoSender;

    @Override
    public void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = PhotoUtils.getUrl(metadata, mediaProperties.maxPhotoDimensions());
        var photo = InputFileDTO.fromUrl(url, hasSpoiler);

        photoSender.send(photo, null);
    }
}
