package io.github.yvasyliev.telegramforwarderbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Converts objects to and from callback data format.
 *
 * <p>
 * Since Telegram Bot API callback data is limited to 64 characters,
 * this service uses a query string format to encode the data for shortness.
 */
@Service
@RequiredArgsConstructor
public class CallbackDataConverter {
    private static final TypeReference<Map<String, String>> STRING_MAP = new TypeReference<>() {};

    private final ObjectMapper objectMapper;

    /**
     * Converts an object to a callback data string.
     *
     * @param data the object to convert
     * @return the callback data string
     */
    public String toCallbackData(Object data) {
        return UriComponentsBuilder.newInstance()
                .queryParams(MultiValueMap.fromSingleValue(objectMapper.convertValue(data, STRING_MAP)))
                .build()
                .getQuery();
    }

    /**
     * Converts a callback data string back to an object of the specified type.
     *
     * @param callbackData the callback data string
     * @param type         the type to convert to
     * @param <T>          the type parameter
     * @return the converted object
     */
    public <T> T fromCallbackData(String callbackData, Class<T> type) {
        return objectMapper.convertValue(
                UriComponentsBuilder.fromUriString("?" + callbackData).build().getQueryParams().asSingleValueMap(),
                type
        );
    }
}
