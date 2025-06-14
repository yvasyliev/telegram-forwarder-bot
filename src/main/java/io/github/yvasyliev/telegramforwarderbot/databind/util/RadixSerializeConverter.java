package io.github.yvasyliev.telegramforwarderbot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RadixSerializeConverter extends StdConverter<Integer, String> {
    private final TelegramProperties telegramProperties;

    @Override
    public String convert(Integer value) {
        return Integer.toString(value, telegramProperties.radix());
    }
}
