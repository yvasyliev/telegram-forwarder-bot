package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implementation of {@link RedditPostSender} for sending Reddit photo posts.
 */
@RequiredArgsConstructor
public class RedditPhotoSender implements RedditPostSender {
    private final PostSender<InputFileDTO, Message> photoSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var url = post.preview()
                .images()
                .getFirst()
                .source()
                .url();
        var photo = InputFileDTO.fromUrl(url, post.isNsfw());

        photoSender.send(photo, post.title());
    }
}
