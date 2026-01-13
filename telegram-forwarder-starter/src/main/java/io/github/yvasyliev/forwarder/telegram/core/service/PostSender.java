package io.github.yvasyliev.forwarder.telegram.core.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * A functional interface for sending posts to Telegram.
 *
 * @param <T> the type of the method to be sent
 * @param <R> the type of the response received after sending
 */
@FunctionalInterface
public interface PostSender<T, R> {
    /**
     * Sends the given method to Telegram.
     *
     * @param method the method to be sent
     * @return the response received after sending
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    R send(T method) throws IOException, TelegramApiException;
}
