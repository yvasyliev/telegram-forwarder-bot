package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.dto.AbstractTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.mapper.CallbackDataMapper;
import io.github.yvasyliev.telegramforwarderbot.service.command.CallbackQueryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackQueryHandler implements TelegramEventHandler<CallbackQuery> {
    private final CallbackDataConverter callbackDataConverter;
    private final CallbackDataMapper callbackDataMapper;
    private final ApplicationContext applicationContext;

    @Override
    @SuppressWarnings("unchecked")
    public void handle(CallbackQuery callbackQuery) {
        var telegramCallbackData = callbackDataConverter.fromCallbackData(
                callbackQuery.getData(),
                AbstractTelegramCommandCallbackDataDTO.class
        );
        var callbackData = callbackDataMapper.map(telegramCallbackData);
        var commandName = callbackData.getCommand();

        try {
            applicationContext.getBean(commandName, CallbackQueryCommand.class).execute(callbackQuery, callbackData);
        } catch (BeansException e) {
            log.atWarn()
                    .addArgument(commandName)
                    .addArgument(() -> applicationContext.getBeanNamesForType(CallbackQueryCommand.class))
                    .log("Unknown callback query command: {}, available commands: {}");
        }
    }
}
