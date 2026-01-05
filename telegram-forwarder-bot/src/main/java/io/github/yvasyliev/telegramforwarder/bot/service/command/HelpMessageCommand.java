package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Command to send a help message to the user.
 * This command is typically used to provide users with information on how to use the bot.
 */
@Service("/help")
@RequiredArgsConstructor
public class HelpMessageCommand implements MessageCommand {
    private final SendMessageMapper sendMessageMapper;
    private final TelegramClient telegramClient;

    @Override
    public void execute(Message message) throws TelegramApiException {
        var sendMessage = sendMessageMapper.map(message, "help");

        telegramClient.execute(sendMessage);
    }
}
