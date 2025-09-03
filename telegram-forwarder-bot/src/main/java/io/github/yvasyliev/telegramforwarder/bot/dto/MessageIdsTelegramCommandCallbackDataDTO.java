package io.github.yvasyliev.telegramforwarder.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.yvasyliev.telegramforwarder.bot.databind.util.RadixDeserializeConverter;
import io.github.yvasyliev.telegramforwarder.bot.databind.util.RadixSerializeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) for handling message IDs in Telegram command callbacks.
 * This class extends the abstract class {@link AbstractTelegramCommandCallbackDataDTO}
 * and includes fields for the first message ID and the count of messages.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public non-sealed class MessageIdsTelegramCommandCallbackDataDTO extends AbstractTelegramCommandCallbackDataDTO {
    @JsonProperty("m")
    @JsonDeserialize(converter = RadixDeserializeConverter.class)
    @JsonSerialize(converter = RadixSerializeConverter.class)
    private Integer firstMessageId;

    @JsonProperty("s")
    @JsonDeserialize(converter = RadixDeserializeConverter.class)
    @JsonSerialize(converter = RadixSerializeConverter.class)
    private Integer messageCount;
}
