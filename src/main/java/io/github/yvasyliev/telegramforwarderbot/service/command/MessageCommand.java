package io.github.yvasyliev.telegramforwarderbot.service.command;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FunctionalInterface
public interface MessageCommand {
    void execute(Message message) throws TelegramApiException;
}
