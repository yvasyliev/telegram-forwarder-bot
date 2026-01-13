package io.github.yvasyliev.forwarder.telegram.bot.service;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * A functional interface for handling Telegram events.
 *
 * @param <T> the type of the event, which extends BotApiObject
 */
@FunctionalInterface
public interface TelegramEventHandler<T extends BotApiObject> {
    /**
     * Handles a Telegram event of type T.
     *
     * @param event the event to handle
     */
    void handle(T event);
}
