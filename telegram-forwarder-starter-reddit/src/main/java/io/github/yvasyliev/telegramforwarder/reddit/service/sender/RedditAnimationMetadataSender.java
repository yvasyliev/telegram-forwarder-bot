package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Implementation of {@link RedditMetadataSender} for sending animation metadata.
 */
@RequiredArgsConstructor
public class RedditAnimationMetadataSender implements RedditMetadataSender {
    private final PostSender<InputFileDTO, Message> animationSender;

    @Override
    public void send(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = metadata.source().gif();
        var animation = InputFileDTO.fromUrl(url, hasSpoiler);

        animationSender.send(animation, null);
    }
}
