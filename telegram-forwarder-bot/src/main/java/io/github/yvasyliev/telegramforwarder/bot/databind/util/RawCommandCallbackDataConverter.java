package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.CommandCallbackDataMapper;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.util.StdConverter;

/**
 * Converter that converts {@link CommandCallbackData} to {@link RawCommandCallbackData}
 * using {@link CommandCallbackDataMapper}.
 */
@RequiredArgsConstructor
public class RawCommandCallbackDataConverter extends StdConverter<CommandCallbackData, RawCommandCallbackData> {
    private final CommandCallbackDataMapper commandCallbackDataMapper;

    @Override
    public RawCommandCallbackData convert(CommandCallbackData commandCallbackData) {
        return commandCallbackDataMapper.map(commandCallbackData);
    }
}
