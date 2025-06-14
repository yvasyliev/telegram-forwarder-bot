package io.github.yvasyliev.telegramforwarderbot.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(@JsonSubTypes.Type(MessageIdsCommandCallbackDataDTO.class))
@Data
public abstract sealed class AbstractCommandCallbackDataDTO permits MessageIdsCommandCallbackDataDTO {
    private String command;
}
