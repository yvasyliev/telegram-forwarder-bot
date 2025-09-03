package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.AbstractCommandCallbackDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Aspect for sending responses to callback queries in Telegram.
 * It answers the callback query with a predefined text based on the command.
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CallbackQueryResponseSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Sends an answer to a callback query.
     *
     * @param callbackQuery the callback query to answer
     * @param callbackData  the data associated with the callback command
     * @throws TelegramApiException if there is an error sending the answer
     */
    @Before("io.github.yvasyliev.telegramforwarder.bot.util.Pointcuts.executeCallbackQueryCommand() && args"
            + "(callbackQuery, callbackData)")
    public void answerCallbackQuery(
            CallbackQuery callbackQuery,
            AbstractCommandCallbackDataDTO callbackData
    ) throws TelegramApiException {
        var callbackAnswerText = telegramProperties.postControls()
                .buttons()
                .get(callbackData.getCommand())
                .callbackAnswerText();
        var answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .text(callbackAnswerText)
                .build();

        telegramClient.execute(answerCallbackQuery);
    }
}
