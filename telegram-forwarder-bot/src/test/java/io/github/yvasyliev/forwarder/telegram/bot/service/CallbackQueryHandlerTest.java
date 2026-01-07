package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.service.command.CallbackQueryCommand;
import io.github.yvasyliev.forwarder.telegram.bot.util.CommandCallbackDataConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackQueryHandlerTest {
    private static final String COMMAND_NAME = "test_command";
    @Mock private CommandCallbackDataConverter commandCallbackDataConverter;
    @Mock private CallbackQueryCommand<CommandCallbackData> callbackQueryCommand;
    @Mock private CallbackQuery callbackQuery;
    private TelegramEventHandler<CallbackQuery> callbackQueryHandler;

    @BeforeEach
    void setUp() {
        callbackQueryHandler = new CallbackQueryHandler(
                commandCallbackDataConverter,
                Map.of(COMMAND_NAME, callbackQueryCommand)
        );
    }

    @Test
    void shouldExecuteCommand() {
        var commandCallbackData = new CommandCallbackData(COMMAND_NAME, null);

        when(commandCallbackDataConverter.convert(callbackQuery)).thenReturn(commandCallbackData);

        callbackQueryHandler.handle(callbackQuery);

        verify(callbackQueryCommand).execute(callbackQuery, commandCallbackData);
    }

    @Test
    void shouldHandleUnknownCommand() {
        var commandCallbackData = new CommandCallbackData("unknown_command", null);

        when(commandCallbackDataConverter.convert(callbackQuery)).thenReturn(commandCallbackData);

        assertDoesNotThrow(() -> callbackQueryHandler.handle(callbackQuery));
    }
}
