package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsEditMessageTextProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.EditMessageTextMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Aspect that edits the text of the post controls message after executing a callback query command.
 * It updates the message text based on the command executed and handles specific Telegram API exceptions.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PostControlsMessageTextEditor {
    private final TelegramClient telegramClient;
    private final EditMessageTextMapper editMessageTextMapper;
    private final PostControlsEditMessageTextProperties editMessageTextProperties;

    /**
     * After returning advice that edits the post controls message text after executing a callback query command.
     *
     * @param callbackQuery the callback query that triggered the command
     * @param callbackData  the data associated with the command
     */
    @AfterReturning("io.github.yvasyliev.telegramforwarder.bot.util.Pointcuts.executePostControlsCallbackQueryCommand()"
            + " && args(callbackQuery, callbackData)")
    public void editPostControlsMessageText(CallbackQuery callbackQuery, CommandCallbackData callbackData) {
        var editMessageText = editMessageTextMapper.map(
                (Message) callbackQuery.getMessage(),
                editMessageTextProperties.options().get(callbackData.command())
        );

        try {
            telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            if (!isSuppressedException(e)) {
                log.error("Failed to edit post controls message text", e);
            }
        }
    }

    private boolean isSuppressedException(TelegramApiException telegramApiException) {
        return telegramApiException instanceof TelegramApiRequestException e
                && editMessageTextProperties.suppressedApiResponses().contains(e.getApiResponse());
    }
}
