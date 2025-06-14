package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service("/stop")
@RequiredArgsConstructor
public class StopCommand implements MessageCommand {
    private final TelegramTemplateEngine templateEngine;
    private final TelegramClient telegramClient;
    private final ApplicationContext applicationContext;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void execute(Message message) throws TelegramApiException {
        var sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(templateEngine.process("shuttingdown"))
                .parseMode(ParseMode.HTML)
                .build();

        telegramClient.execute(sendMessage);
        SpringApplication.exit(applicationContext);
    }
}
