package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards a photo from a Reddit link to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class PhotoForwarder implements Forwarder {
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
