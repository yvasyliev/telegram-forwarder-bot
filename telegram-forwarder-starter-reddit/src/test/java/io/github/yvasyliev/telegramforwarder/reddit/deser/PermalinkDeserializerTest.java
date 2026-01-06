package io.github.yvasyliev.telegramforwarder.reddit.deser;

import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.ValueDeserializer;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PermalinkDeserializerTest {
    private static final String HOST = "https://www.reddit.com";
    private static final ValueDeserializer<Object> PERMALINK_DESERIALIZER
            = new PermalinkDeserializer(new RedditProperties(URI.create(HOST), null, null, null));

    @Test
    void testDeserialize() throws IOException {
        var text = "/r/test/comments/abcd1234/test_post/";
        var p = mock(JsonParser.class);
        var expected = URI.create(HOST + text).toURL();

        when(p.getValueAsString()).thenReturn(text);

        var actual = assertDoesNotThrow(() -> PERMALINK_DESERIALIZER.deserialize(p, null));

        assertEquals(expected, actual);
    }
}
