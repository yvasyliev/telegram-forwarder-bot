package io.github.yvasyliev.telegramforwarder.bot.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandCallbackDataConverterTest {
    private static final String KEY1 = "key1";
    private static final String VALUE1 = "value1";
    private static final String KEY2 = "key2";
    private static final String VALUE2 = "value2";
    private static final String EQUALS_SIGN = "=";
    private static final String AMPERSAND = "&";
    private static final String PAIR1 = KEY1 + EQUALS_SIGN + VALUE1;
    private static final String PAIR2 = KEY2 + EQUALS_SIGN + VALUE2;
    private static final Map<String, String> COMMAND_CALLBACK_DATA_MAP = Map.of(KEY1, VALUE1, KEY2, VALUE2);
    @InjectMocks private CommandCallbackDataConverter commandCallbackDataConverter;
    @Mock private JsonMapper jsonMapper;

    @Test
    void testConvertCommandCallbackData() {
        var commandCallbackData = mock(CommandCallbackData.class);

        when(jsonMapper.convertValue(
                eq(commandCallbackData),
                ArgumentMatchers.<TypeReference<Map<String, String>>>any()
        )).thenReturn(COMMAND_CALLBACK_DATA_MAP);

        var actual = commandCallbackDataConverter.convert(commandCallbackData);

        assertThat(actual).contains(PAIR1).contains(AMPERSAND).contains(PAIR2);
    }

    @Test
    void testConvertCallbackQuery() {
        var callbackQuery = new CallbackQuery();
        var expected = mock(CommandCallbackData.class);

        callbackQuery.setData(PAIR1 + AMPERSAND + PAIR2);

        when(jsonMapper.convertValue(COMMAND_CALLBACK_DATA_MAP, CommandCallbackData.class)).thenReturn(expected);

        var actual = commandCallbackDataConverter.convert(callbackQuery);

        assertEquals(expected, actual);
    }
}
