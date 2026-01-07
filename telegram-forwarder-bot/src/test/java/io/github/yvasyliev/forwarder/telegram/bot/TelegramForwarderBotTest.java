package io.github.yvasyliev.forwarder.telegram.bot;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramBotProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TelegramForwarderBotTest {
    private static final String BOT_TOKEN = "test-bot-token";
    private TelegramForwarderBot telegramForwarderBot;
    @Mock private LongPollingUpdateConsumer longPollingUpdateConsumer;

    @BeforeEach
    void setUp() {
        var botProperties = new TelegramBotProperties();
        telegramForwarderBot = new TelegramForwarderBot(botProperties, longPollingUpdateConsumer);

        botProperties.setBotToken(BOT_TOKEN);
    }

    @Test
    void testGetBotToken() {
        var actual = telegramForwarderBot.getBotToken();

        assertEquals(BOT_TOKEN, actual);
    }

    @Test
    void testGetUpdatesConsumer() {
        var actual = telegramForwarderBot.getUpdatesConsumer();

        assertEquals(longPollingUpdateConsumer, actual);
    }
}
