package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StopCommandTest {
    @InjectMocks private StopCommand command;
    @Mock private TelegramTemplateEngine templateEngine;
    @Mock private TelegramClient telegramClient;
    @Mock private ApplicationContext applicationContext;

    @Test
    void testExecute() throws TelegramApiException {
        var chatId = 123456789L;
        var message = Message.builder().chat(new Chat(chatId, "Test Chat")).build();
        var text = "Stopping the bot...";
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();

        when(templateEngine.process(eq("shuttingdown"), refEq(new Context()))).thenReturn(text);

        try (var springApplication = mockStatic(SpringApplication.class)) {
            command.execute(message);

            springApplication.verify(() -> SpringApplication.exit(applicationContext));
        }

        verify(telegramClient).execute(sendMessage);
    }
}
