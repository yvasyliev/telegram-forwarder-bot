package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlSenderTest {
    @InjectMocks private UrlSender sender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramTemplateEngine templateEngine;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var url = mock(URL.class);
        var message = "Test message";
        var adminId = 123456789L;
        var context = new Context();
        var text = "Test text";
        var sendMessage = SendMessage.builder()
                .chatId(adminId)
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();
        var expected = mock(Message.class);

        context.setVariable("text", message);
        context.setVariable("url", url);

        when(telegramProperties.adminId()).thenReturn(adminId);
        when(templateEngine.process(eq("url"), refEq(context))).thenReturn(text);
        when(telegramClient.execute(sendMessage)).thenReturn(expected);

        var actual = sender.send(url, message);

        assertEquals(expected, actual);
        verify(telegramClient).execute(sendMessage);
    }
}
