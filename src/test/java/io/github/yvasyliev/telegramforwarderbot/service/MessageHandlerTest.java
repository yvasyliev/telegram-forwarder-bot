package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.service.command.MessageCommand;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageHandlerTest {
    @InjectMocks private MessageHandler messageHandler;
    @Mock private ApplicationContext applicationContext;
    private final Message message = new Message();

    @Test
    void shouldIgnoreNonCommandMessages() {
        messageHandler.handle(message);

        verifyNoInteractions(applicationContext);
    }

    @Nested
    class CommandMessageTests {
        private static final String COMMAND_NAME = "/testCommand";

        @BeforeEach
        void setUp() {
            message.setText(COMMAND_NAME);
            message.setEntities(List.of(new MessageEntity(
                    EntityType.BOTCOMMAND,
                    NumberUtils.INTEGER_ZERO,
                    COMMAND_NAME.length()
            )));
        }

        @Test
        void shouldExecuteCommand() throws TelegramApiException {
            var messageCommand = mock(MessageCommand.class);

            when(applicationContext.getBean(COMMAND_NAME, MessageCommand.class)).thenReturn(messageCommand);

            messageHandler.handle(message);

            verify(messageCommand).execute(message);
        }

        @Test
        void shouldHandleUnknownCommand() {
            when(applicationContext.getBean(COMMAND_NAME, MessageCommand.class))
                    .thenThrow(NoSuchBeanDefinitionException.class);

            assertDoesNotThrow(() -> messageHandler.handle(message));
        }

        @Test
        void shouldHandleCommandExecutionFailure() throws TelegramApiException {
            var messageCommand = mock(MessageCommand.class);

            when(applicationContext.getBean(COMMAND_NAME, MessageCommand.class)).thenReturn(messageCommand);
            doThrow(TelegramApiException.class).when(messageCommand).execute(message);

            assertDoesNotThrow(() -> messageHandler.handle(message));

            verify(messageCommand).execute(message);
        }
    }
}