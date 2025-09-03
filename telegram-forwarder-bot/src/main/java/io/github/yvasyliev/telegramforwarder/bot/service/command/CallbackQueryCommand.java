package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.AbstractCommandCallbackDataDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Interface for handling callback queries with specific command data.
 *
 * @param <T> the type of command callback data
 */
@FunctionalInterface
public interface CallbackQueryCommand<T extends AbstractCommandCallbackDataDTO> {
    /**
     * Executes the command with the provided callback query and data.
     *
     * @param callbackQuery the callback query containing the command
     * @param callbackData  the data associated with the command
     */
    @PreAuthorize("hasRole('ADMIN')")
    void execute(CallbackQuery callbackQuery, T callbackData);
}
