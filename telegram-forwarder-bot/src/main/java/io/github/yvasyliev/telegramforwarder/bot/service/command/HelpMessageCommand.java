package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

/**
 * Command to send a help message to the user.
 * This command is typically used to provide users with information on how to use the bot.
 */
@RequiredArgsConstructor
public class HelpMessageCommand implements MessageCommand {
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

    @Override
    public void execute(Message message) throws TelegramApiException {
        var text = templateEngine.process("help", new Context());
        var sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
    }
}
