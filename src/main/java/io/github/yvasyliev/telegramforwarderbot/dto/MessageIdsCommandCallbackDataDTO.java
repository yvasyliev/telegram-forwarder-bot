package io.github.yvasyliev.telegramforwarderbot.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.yvasyliev.telegramforwarderbot.databind.util.MessageIdsCallbackDataSerializeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(converter = MessageIdsCallbackDataSerializeConverter.class)
public non-sealed class MessageIdsCommandCallbackDataDTO extends AbstractCommandCallbackDataDTO {
    private List<Integer> messageIds;
}
