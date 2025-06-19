package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.AnimationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards animation metadata.
 */
@Service
@RequiredArgsConstructor
public class AnimationMetadataForwarder implements MetadataForwarder {
    private final AnimationSender animationSender;

    @Override
    public void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException {
        var url = metadata.source().gif();
        var animation = InputFileDTO.fromUrl(url, hasSpoiler);

        animationSender.sendAnimation(animation, null);
    }
}
