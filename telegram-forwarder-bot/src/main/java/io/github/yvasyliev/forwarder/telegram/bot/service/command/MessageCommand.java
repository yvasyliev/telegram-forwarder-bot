package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Functional interface for executing commands based on Telegram messages.
 * Implementations should define how to handle a given message.
 */
@FunctionalInterface
public interface MessageCommand {
    /**
     * Executes the command with the provided message.
     *
     * @param message the Telegram message to process
     * @throws TelegramApiException if an error occurs while processing the message
     */
    void execute(Message message) throws TelegramApiException;
}
