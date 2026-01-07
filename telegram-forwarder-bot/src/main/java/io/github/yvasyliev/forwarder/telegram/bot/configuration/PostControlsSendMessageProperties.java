package io.github.yvasyliev.forwarder.telegram.bot.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Configuration properties for post controls send message in the Telegram bot.
 *
 * @param text        the text of the message to be sent
 * @param replyMarkup the inline keyboard markup for the message
 */
@ConfigurationProperties("telegram.post-controls.send-message")
@Validated
public record PostControlsSendMessageProperties(
        @NotBlank String text,
        @NotNull @Valid InlineKeyboardMarkupProperties replyMarkup
) {
    /**
     * Properties for inline keyboard markup.
     *
     * @param keyboard the keyboard layout
     */
    public record InlineKeyboardMarkupProperties(@NotEmpty List<List<InlineKeyboardButtonProperties>> keyboard) {
        /**
         * Properties for an inline keyboard button.
         *
         * @param command the command associated with the button
         * @param text    the text displayed on the button
         */
        public record InlineKeyboardButtonProperties(String command, String text) {}
    }
}
