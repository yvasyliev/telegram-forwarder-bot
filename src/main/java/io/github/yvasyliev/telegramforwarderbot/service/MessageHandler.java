package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.service.command.MessageCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Handles incoming Telegram messages and executes the corresponding command.
 * It checks if the message is a command and retrieves the command name from the message entities.
 * If the command is found, it executes the corresponding {@link MessageCommand}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageHandler implements TelegramEventHandler<Message> {
    private final ApplicationContext applicationContext;

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

        try {
            applicationContext.getBean(commandName, MessageCommand.class).execute(message);
        } catch (BeansException e) {
            log.atWarn()
                    .addArgument(commandName)
                    .addArgument(() -> applicationContext.getBeanNamesForType(MessageCommand.class))
                    .log("Unknown command: {}, available commands: {}");
        } catch (TelegramApiException e) {
            log.error("Failed to execute command: {}", commandName, e);
        }
    }
}
