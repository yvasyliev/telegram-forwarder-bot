package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Functional interface for forwarding links.
 * Implementations should define how to forward a given link.
 */
@FunctionalInterface
public interface Forwarder {
    /**
     * Forwards the specified link.
     *
     * @param link the link to forward
     * @throws IOException          if an I/O error occurs during forwarding
     * @throws TelegramApiException if a Telegram API error occurs during forwarding
     */
    void forward(Link link) throws IOException, TelegramApiException;
}
