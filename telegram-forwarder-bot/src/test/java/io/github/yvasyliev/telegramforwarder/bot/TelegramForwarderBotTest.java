package io.github.yvasyliev.telegramforwarder.bot;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.context.Context;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramForwarderBotTest {
    private static final long ADMIN_ID = 123456789;
    private TelegramForwarderBot bot;
    @Mock private User me;
    @Mock private TelegramTemplateEngine templateEngine;
    @Mock private TelegramClient telegramClient;

    @BeforeEach
    void setUp() {
        bot = new TelegramForwarderBot(null, null, me, ADMIN_ID, templateEngine, telegramClient);
    }

    @Test
    void testInit() throws TelegramApiException {
        var firstName = "TestBot";
        var text = "Bot is running";
        var context = new Context();
        var sendMessage = SendMessage.builder()
                .chatId(ADMIN_ID)
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();

        context.setVariable("botName", firstName);

        when(me.getFirstName()).thenReturn(firstName);
        when(templateEngine.process(eq("running"), refEq(context))).thenReturn(text);

        bot.init();

        verify(telegramClient).execute(sendMessage);
    }

    @Test
    void testShutdown() throws TelegramApiException {
        var text = "Bot is shutting down";
        var sendMessage = SendMessage.builder()
                .chatId(ADMIN_ID)
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();

        when(templateEngine.process(eq("shutdown"), refEq(new Context()))).thenReturn(text);

        bot.shutdown();

        verify(telegramClient).execute(sendMessage);
    }
}
