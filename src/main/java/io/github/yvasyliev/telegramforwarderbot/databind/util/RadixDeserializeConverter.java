package io.github.yvasyliev.telegramforwarderbot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RadixDeserializeConverter extends StdConverter<String, Integer> {
    private final TelegramProperties telegramProperties;

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value, telegramProperties.radix());
    }
}
