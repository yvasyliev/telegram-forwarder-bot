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
 * Forwards animation metadata.
 */
@Service
@RequiredArgsConstructor
public class AnimationMetadataForwarder implements MetadataForwarder {
    private final PostSender<InputFileDTO, Message> animationSender;

    @Override
    public void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = metadata.source().gif();
        var animation = InputFileDTO.fromUrl(url, hasSpoiler);

        animationSender.send(animation, null);
    }
}
