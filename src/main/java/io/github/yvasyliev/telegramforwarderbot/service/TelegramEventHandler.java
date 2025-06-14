package io.github.yvasyliev.telegramforwarderbot.service;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

@FunctionalInterface
public interface TelegramEventHandler<T extends BotApiObject> {
    void handle(T event);
}
