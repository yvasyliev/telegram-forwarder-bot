package io.github.yvasyliev.telegramforwarder.core.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@FunctionalInterface
public interface PostSender<T, R> {
    R send(T source, String text) throws IOException, TelegramApiException;
}
