package io.github.yvasyliev.telegramforwarder.bot.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.yvasyliev.telegramforwarder.bot.databind.util.RawCommandCallbackDataConverter;

import java.util.List;

/**
 * Data Transfer Object representing command callback data.
 *
 * @param command    the command associated with the callback
 * @param messageIds the list of message IDs related to the command
 */
@JsonSerialize(converter = RawCommandCallbackDataConverter.class)
public record CommandCallbackData(String command, List<Integer> messageIds) {}
