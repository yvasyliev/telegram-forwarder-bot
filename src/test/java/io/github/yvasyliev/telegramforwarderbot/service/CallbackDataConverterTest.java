package io.github.yvasyliev.telegramforwarderbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackDataConverterTest {
    private static final String VALUE1 = "value1";
    private static final CallbackDataConverter CONVERTER = new CallbackDataConverter(new ObjectMapper());

    @Test
    void testToCallbackData() {
        String[] expected = {"field1=value1", "field2=42"};

        var actual = CONVERTER.toCallbackData(new TestDTO(VALUE1, 42));

        assertThat(actual).contains("&");
        assertThat(actual.split("&")).containsExactlyInAnyOrder(expected);
    }

    @Test
    void testFromCallbackData() {
        var callbackData = "field1=value1&field2=42";
        var expected = new TestDTO(VALUE1, 42);

        var actual = CONVERTER.fromCallbackData(callbackData, TestDTO.class);

        assertEquals(expected, actual);
    }

    private record TestDTO(String field1, int field2) {}
}
