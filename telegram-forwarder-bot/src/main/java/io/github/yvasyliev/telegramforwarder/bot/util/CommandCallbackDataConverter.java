package io.github.yvasyliev.telegramforwarder.bot.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.CommandCallbackDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Converter for command callback data to and from query string format.
 */
@Component
@RequiredArgsConstructor
public class CommandCallbackDataConverter {
    private static final TypeReference<Map<String, String>> STRING_MAP = new TypeReference<>() {};

    private final ObjectMapper objectMapper;
    private final CommandCallbackDataMapper commandCallbackDataMapper;

    /**
     * Converts CommandCallbackData to a query string.
     *
     * @param commandCallbackData the command callback data to convert
     * @return the query string representation of the command callback data
     */
    public String convert(CommandCallbackData commandCallbackData) {
        return UriComponentsBuilder.newInstance()
                .queryParams(MultiValueMap.fromSingleValue(objectMapper.convertValue(commandCallbackData, STRING_MAP)))
                .build()
                .getQuery();
    }

    /**
     * Converts a {@link CallbackQuery} to CommandCallbackData.
     *
     * @param callbackQuery the callback query to convert
     * @return the {@link CommandCallbackData} representation of the callback query
     */
    public CommandCallbackData convert(CallbackQuery callbackQuery) {
        var rawCommandCallbackDataMap = UriComponentsBuilder.fromUriString("?" + callbackQuery.getData())
                .build()
                .getQueryParams()
                .asSingleValueMap();
        // TODO: CommandCallbackData
        var rawCommandCallbackData = objectMapper.convertValue(rawCommandCallbackDataMap, RawCommandCallbackData.class);

        return commandCallbackDataMapper.map(rawCommandCallbackData);
    }
}
