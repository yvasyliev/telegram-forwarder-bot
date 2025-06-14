package io.github.yvasyliev.telegramforwarderbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.yvasyliev.telegramforwarderbot.databind.util.RadixDeserializeConverter;
import io.github.yvasyliev.telegramforwarderbot.databind.util.RadixSerializeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
