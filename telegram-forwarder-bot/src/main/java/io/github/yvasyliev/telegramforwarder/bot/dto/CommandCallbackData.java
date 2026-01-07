package io.github.yvasyliev.telegramforwarder.bot.dto;

import io.github.yvasyliev.telegramforwarder.bot.databind.util.CommandCallbackDataConverter;
import io.github.yvasyliev.telegramforwarder.bot.databind.util.RawCommandCallbackDataConverter;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Data Transfer Object representing command callback data.
 *
 * @param command    the command associated with the callback
 * @param messageIds the list of message IDs related to the command
 */
@JsonSerialize(converter = RawCommandCallbackDataConverter.class)
@JsonDeserialize(converter = CommandCallbackDataConverter.class)
public record CommandCallbackData(String command, List<Integer> messageIds) {}
