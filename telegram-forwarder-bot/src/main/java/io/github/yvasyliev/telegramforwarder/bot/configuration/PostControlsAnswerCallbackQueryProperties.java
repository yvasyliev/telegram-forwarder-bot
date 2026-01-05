package io.github.yvasyliev.telegramforwarder.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * Configuration properties for post controls answer callback query in the Telegram bot.
 *
 * @param options the map of options for answering callback queries
 */
@ConfigurationProperties("telegram.post-controls.answer-callback-query")
@Validated
public record PostControlsAnswerCallbackQueryProperties(@NotEmpty Map<String, AnswerCallbackQueryProperties> options) {
    /**
     * Properties for answering a callback query.
     *
     * @param text the text to be sent in the answer
     */
    public record AnswerCallbackQueryProperties(String text) {}
}
