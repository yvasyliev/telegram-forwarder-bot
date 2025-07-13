package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.PhotoSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards a photo from a Reddit link to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class PhotoForwarder implements Forwarder {
    private final PhotoSender photoSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var url = link.preview()
                .images()
                .getFirst()
                .source()
                .url();
        var photo = InputFileDTO.fromUrl(url, link.isNsfw());

        photoSender.sendPhoto(photo, link.title());
    }
}
