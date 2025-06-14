package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CallbackQueryResponseSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    @Before("io.github.yvasyliev.telegramforwarderbot.util.Pointcuts.executeCallbackQueryCommand() && args" +
            "(callbackQuery, callbackData)")
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
