package io.github.yvasyliev.forwarder.telegram.core.dto;

/**
 * A DTO Telegram Bot API method for sending a text message to Telegram.
 *
 * @param text the text of the message to be sent
 */
public record SendMessageDTO(String text) implements PartialBotApiMethodDTO {
    @Override
    public void close() {}
}
