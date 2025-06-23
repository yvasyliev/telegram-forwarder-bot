package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * After returning advice that edits the post controls message text after executing a callback query command.
     *
     * @param callbackQuery the callback query that triggered the command
     * @param callbackData  the data associated with the command
     */
    @AfterReturning("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.executePostControlsCallbackQueryCommand"
            + "() && args(callbackQuery, callbackData)")
    public void editPostControlsMessageText(CallbackQuery callbackQuery, AbstractCommandCallbackDataDTO callbackData) {
        var postControls = telegramProperties.postControls();
        var messageText = postControls.buttons().get(callbackData.getCommand()).messageText();
        var message = (Message) callbackQuery.getMessage();
        var editMessageText = EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text(messageText)
                .replyMarkup(message.getReplyMarkup())
                .build();

        try {
            telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            if (!isSuppressedException(e)) {
                log.error("Failed to edit post controls message text", e);
            }
        }
    }

    private boolean isSuppressedException(TelegramApiException e) {
        var ignoredApiResponses = telegramProperties.postControls().ignoredApiResponses();
        return e instanceof TelegramApiRequestException ex && ignoredApiResponses.contains(ex.getApiResponse());
    }
}
