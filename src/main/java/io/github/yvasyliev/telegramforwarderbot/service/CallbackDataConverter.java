package io.github.yvasyliev.telegramforwarderbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CallbackDataConverter {
    private static final TypeReference<Map<String, String>> STRING_MAP = new TypeReference<>() {};

    private final ObjectMapper objectMapper;

    public String toCallbackData(Object data) {
        return UriComponentsBuilder.newInstance()
                .queryParams(MultiValueMap.fromSingleValue(objectMapper.convertValue(data, STRING_MAP)))
                .build()
                .getQuery();
    }

    public <T> T fromCallbackData(String callbackData, Class<T> type) {
        return objectMapper.convertValue(
                UriComponentsBuilder.fromUriString("?" + callbackData).build().getQueryParams().asSingleValueMap(),
                type
        );
    }
}
