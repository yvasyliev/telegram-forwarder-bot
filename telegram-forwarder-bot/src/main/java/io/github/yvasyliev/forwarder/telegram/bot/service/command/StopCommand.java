package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Stops the bot and sends a shutdown message to the chat.
 * This command can only be executed by users with the {@code ADMIN} role.
 */
@Service("/stop")
@RequiredArgsConstructor
public class StopCommand implements MessageCommand {
    private final SendMessageMapper sendMessageMapper;
    private final TelegramClient telegramClient;
    private final ApplicationContext applicationContext;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void execute(Message message) throws TelegramApiException {
        var sendMessage = sendMessageMapper.map(message, "shuttingdown");

        telegramClient.execute(sendMessage);
        SpringApplication.exit(applicationContext);
    }
}
