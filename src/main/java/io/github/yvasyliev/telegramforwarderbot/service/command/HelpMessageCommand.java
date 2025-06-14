package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@RequiredArgsConstructor
public class HelpMessageCommand implements MessageCommand {
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;

    @Override
    public void execute(Message message) throws TelegramApiException {
        var text = templateEngine.process("help");
        var sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
    }
}
