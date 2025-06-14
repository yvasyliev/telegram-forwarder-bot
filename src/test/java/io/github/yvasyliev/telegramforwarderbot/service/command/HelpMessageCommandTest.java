package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpMessageCommandTest {
    @InjectMocks private HelpMessageCommand command;
    @Mock private TelegramTemplateEngine templateEngine;
    @Mock private TelegramClient telegramClient;

    @Test
    void testExecute() throws TelegramApiException {
        var chatId = 123L;
        var message = Message.builder().chat(new Chat(chatId, "private")).build();
        var text = "Help message";
        var expected = SendMessage.builder().chatId(chatId).text(text).parseMode(ParseMode.HTML).build();

        when(templateEngine.process("help")).thenReturn(text);

        command.execute(message);

        verify(telegramClient).execute(expected);
    }
}