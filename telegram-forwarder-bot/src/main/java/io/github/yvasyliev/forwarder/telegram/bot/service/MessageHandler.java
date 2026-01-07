package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.service.command.MessageCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

/**
 * Handles incoming Telegram messages and executes the corresponding command.
 * It checks if the message is a command and retrieves the command name from the message entities.
 * If the command is found, it executes the corresponding {@link MessageCommand}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageHandler implements TelegramEventHandler<Message> {
    private final Map<String, MessageCommand> messageCommands;

    @Override
    public void handle(Message message) {
        if (!message.isCommand()) {
            return;
        }

        var commandName = IterableUtils.find(
                        message.getEntities(),
                        entity -> NumberUtils.INTEGER_ZERO.equals(entity.getOffset())
                )
                .getText();
        var messageCommand = messageCommands.getOrDefault(commandName, _ -> log.warn(
                "No command found for name: {}, available commands: {}",
                commandName,
                messageCommands.keySet()
        ));

        try {
            messageCommand.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to execute command: {}", commandName, e);
        }
    }
}
