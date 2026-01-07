package io.github.yvasyliev.forwarder.telegram.bot.aspect;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsAnswerCallbackQueryProperties;
import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.AnswerCallbackQueryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
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
    private final PostControlsAnswerCallbackQueryProperties postControlsAnswerCallbackQueryProperties;
    private final AnswerCallbackQueryMapper answerCallbackQueryMapper;
    private final TelegramClient telegramClient;

    /**
     * Sends an answer to a callback query.
     *
     * @param callbackQuery the callback query to answer
     * @param callbackData  the data associated with the callback command
     * @throws TelegramApiException if there is an error sending the answer
     */
    @Before("io.github.yvasyliev.forwarder.telegram.bot.util.Pointcuts.executeCallbackQueryCommand() && args"
            + "(callbackQuery, callbackData)")
    public void answerCallbackQuery(CallbackQuery callbackQuery, CommandCallbackData callbackData)
            throws TelegramApiException {
        var answerCallbackQuery = answerCallbackQueryMapper.map(
                callbackQuery,
                postControlsAnswerCallbackQueryProperties.options().get(callbackData.command())
        );

        telegramClient.execute(answerCallbackQuery);
    }
}
