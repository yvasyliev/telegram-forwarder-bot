package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards a photo from a Reddit link to a Telegram chat.
 */
@RequiredArgsConstructor
public class PhotoForwarder implements LinkForwarder {
    private final PostSender<InputFileDTO, Message> photoSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var url = link.preview()
                .images()
                .getFirst()
                .source()
                .url();
        var photo = InputFileDTO.fromUrl(url, link.isNsfw());

        photoSender.send(photo, link.title());
    }
}
