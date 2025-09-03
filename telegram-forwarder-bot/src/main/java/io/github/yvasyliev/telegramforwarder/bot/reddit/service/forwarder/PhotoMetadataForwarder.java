package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.bot.reddit.util.PhotoUtils;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards a photo from a Reddit link metadata to a Telegram chat.
 */
@Service
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
