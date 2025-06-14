package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@FunctionalInterface
public interface CallbackQueryCommand<T extends AbstractCommandCallbackDataDTO> {
    @PreAuthorize("hasRole('ADMIN')")
    void execute(CallbackQuery callbackQuery, T callbackData);
}
