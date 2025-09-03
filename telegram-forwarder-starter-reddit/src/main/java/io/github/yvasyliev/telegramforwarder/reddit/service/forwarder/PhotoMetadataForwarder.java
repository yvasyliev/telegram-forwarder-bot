package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

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
 * Forwards a photo from a Reddit link metadata to a Telegram chat.
 */
@RequiredArgsConstructor
public class PhotoMetadataForwarder implements MetadataForwarder {
    private final TelegramMediaProperties mediaProperties;
    private final PostSender<InputFileDTO, Message> photoSender;

    @Override
    public void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = PhotoUtils.getUrl(metadata, mediaProperties.maxPhotoDimensions());
        var photo = InputFileDTO.fromUrl(url, hasSpoiler);

        photoSender.send(photo, null);
    }
}
