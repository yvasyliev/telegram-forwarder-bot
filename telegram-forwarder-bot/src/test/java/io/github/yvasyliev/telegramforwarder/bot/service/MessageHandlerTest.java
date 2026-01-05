package io.github.yvasyliev.telegramforwarder.bot.service;

import io.github.yvasyliev.telegramforwarder.bot.service.command.MessageCommand;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageHandlerTest {
    @Test
    void shouldIgnoreNonCommandMessages() {
        var messageHandler = new MessageHandler(null);

        assertDoesNotThrow(() -> messageHandler.handle(new Message()));
    }

    @Nested
    class CommandMessageTests {
        private static final String COMMAND_NAME = "/testCommand";
        @Mock private MessageCommand messageCommand;
        private Message message;

        @BeforeEach
        void setUp() {
            message = new Message();

            message.setText(COMMAND_NAME);
            message.setEntities(List.of(new MessageEntity(
                    EntityType.BOTCOMMAND,
                    NumberUtils.INTEGER_ZERO,
                    COMMAND_NAME.length()
            )));
        }

        @Test
        void shouldExecuteCommand() throws TelegramApiException {
            var messageHandler = new MessageHandler(Map.of(COMMAND_NAME, messageCommand));

            messageHandler.handle(message);

            verify(messageCommand).execute(message);
        }

        @Test
        void shouldHandleUnknownCommand() {
            var messageHandler = new MessageHandler(Map.of());

            assertDoesNotThrow(() -> messageHandler.handle(message));
        }

        @Test
        void shouldHandleCommandExecutionFailure() throws TelegramApiException {
            var messageHandler = new MessageHandler(Map.of(COMMAND_NAME, messageCommand));

            doThrow(TelegramApiException.class).when(messageCommand).execute(message);

            assertDoesNotThrow(() -> messageHandler.handle(message));

            verify(messageCommand).execute(message);
        }
    }
}
