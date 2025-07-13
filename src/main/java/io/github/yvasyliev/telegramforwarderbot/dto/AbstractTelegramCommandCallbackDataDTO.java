package io.github.yvasyliev.telegramforwarderbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * Abstract class for command callback data DTOs specific to Telegram.
 * It is used to handle different types of command callbacks in a polymorphic way.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(@JsonSubTypes.Type(MessageIdsTelegramCommandCallbackDataDTO.class))
@Data
public abstract sealed class AbstractTelegramCommandCallbackDataDTO permits MessageIdsTelegramCommandCallbackDataDTO {
    @JsonProperty("c")
    private String command;
}
