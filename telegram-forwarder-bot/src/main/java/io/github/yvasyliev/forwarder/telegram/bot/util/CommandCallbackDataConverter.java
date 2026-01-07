package io.github.yvasyliev.forwarder.telegram.bot.util;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

/**
 * Converter for command callback data to and from query string format.
 */
@Component
@RequiredArgsConstructor
public class CommandCallbackDataConverter {
    private static final TypeReference<Map<String, String>> STRING_MAP = new TypeReference<>() {};

    private final JsonMapper jsonMapper;

    /**
     * Converts CommandCallbackData to a query string.
     *
     * @param commandCallbackData the command callback data to convert
     * @return the query string representation of the command callback data
     */
    public String convert(CommandCallbackData commandCallbackData) {
        var queryParams = jsonMapper.convertValue(commandCallbackData, STRING_MAP);

        return UriComponentsBuilder.newInstance()
                .queryParams(MultiValueMap.fromSingleValue(queryParams))
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

        return jsonMapper.convertValue(rawCommandCallbackDataMap, CommandCallbackData.class);
    }
}
