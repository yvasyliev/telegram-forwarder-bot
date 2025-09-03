package io.github.yvasyliev.telegramforwarder.bot.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * Abstract class for command callback data DTOs.
 * It is used to handle different types of command callbacks in a polymorphic way.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(@JsonSubTypes.Type(MessageIdsCommandCallbackDataDTO.class))
@Data
public abstract sealed class AbstractCommandCallbackDataDTO permits MessageIdsCommandCallbackDataDTO {
    private String command;
}
