package io.github.yvasyliev.telegramforwarder.core.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Functional interface for sending posts.
 *
 * @param <T> the type of the source object
 * @param <R> the type of the result object
 */
@FunctionalInterface
public interface PostSender<T, R> {
    /**
     * Sends a post from the given source with the specified text.
     *
     * @param source the source object from which the post is sent
     * @param text   the text content of the post
     * @return the result of the send operation
     * @throws IOException          if an I/O error occurs during sending
     * @throws TelegramApiException if a Telegram API error occurs during sending
     */
    R send(T source, String text) throws IOException, TelegramApiException;
}
