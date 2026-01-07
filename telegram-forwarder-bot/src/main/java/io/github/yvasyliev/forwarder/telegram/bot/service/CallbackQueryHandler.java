package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.service.command.CallbackQueryCommand;
import io.github.yvasyliev.forwarder.telegram.bot.util.CommandCallbackDataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;

/**
 * Handles incoming Telegram callback queries by mapping them to commands.
 * It retrieves the command bean from the application context and executes it.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackQueryHandler implements TelegramEventHandler<CallbackQuery> {
    private final CommandCallbackDataConverter commandCallbackDataConverter;
    private final Map<String, CallbackQueryCommand<CommandCallbackData>> callbackQueryCommands;

    @Override
    public void handle(CallbackQuery callbackQuery) {
        var commandCallbackData = commandCallbackDataConverter.convert(callbackQuery);

        getCommand(commandCallbackData).execute(callbackQuery, commandCallbackData);
    }

    private CallbackQueryCommand<CommandCallbackData> getCommand(CommandCallbackData commandCallbackData) {
        return callbackQueryCommands.getOrDefault(
                commandCallbackData.command(),
                (_, _) -> log.warn(
                        "Unknown callback query command: {}, available commands: {}",
                        commandCallbackData.command(),
                        callbackQueryCommands.keySet()
                )
        );
    }
}
