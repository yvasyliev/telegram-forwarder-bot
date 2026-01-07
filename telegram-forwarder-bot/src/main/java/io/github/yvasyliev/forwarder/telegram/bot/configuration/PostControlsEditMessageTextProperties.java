package io.github.yvasyliev.forwarder.telegram.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

/**
 * Configuration properties for post controls edit message text in the Telegram bot.
 *
 * @param options                the map of options for editing message text
 * @param suppressedApiResponses the set of API responses to be suppressed
 */
@ConfigurationProperties("telegram.post-controls.edit-message-text")
@Validated
public record PostControlsEditMessageTextProperties(
        @NotEmpty Map<String, EditMessageTextProperties> options,
        @NotEmpty Set<String> suppressedApiResponses
) {
    /**
     * Properties for editing message text.
     *
     * @param text the new text for the message
     */
    public record EditMessageTextProperties(String text) {}
}
