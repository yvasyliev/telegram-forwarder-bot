package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.util.PhotoUtils;
import io.github.yvasyliev.telegramforwarderbot.service.sender.PhotoSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoMetadataForwarder implements MetadataForwarder {
    private final TelegramProperties telegramProperties;
    private final PhotoSender photoSender;

    @Override
    public void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = PhotoUtils.getUrl(metadata, telegramProperties.maxPhotoDimensions());
        var photo = InputFileDTO.fromUrl(url, hasSpoiler);

        photoSender.sendPhoto(photo, null);
    }
}
