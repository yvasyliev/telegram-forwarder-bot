package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@FunctionalInterface
public interface MetadataForwarder {
    void forward(Link.Metadata metadata, boolean hasSpoiler) throws IOException, TelegramApiException;
}
