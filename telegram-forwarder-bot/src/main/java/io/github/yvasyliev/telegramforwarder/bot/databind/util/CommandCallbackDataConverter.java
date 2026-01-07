package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.CommandCallbackDataMapper;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.util.StdConverter;

/**
 * Converter that converts {@link RawCommandCallbackData} to {@link CommandCallbackData} using
 * {@link CommandCallbackDataMapper}.
 */
@RequiredArgsConstructor
public class CommandCallbackDataConverter extends StdConverter<RawCommandCallbackData, CommandCallbackData> {
    private final CommandCallbackDataMapper commandCallbackDataMapper;

    @Override
    public CommandCallbackData convert(RawCommandCallbackData commandCallbackData) {
        return commandCallbackDataMapper.map(commandCallbackData);
    }
}
