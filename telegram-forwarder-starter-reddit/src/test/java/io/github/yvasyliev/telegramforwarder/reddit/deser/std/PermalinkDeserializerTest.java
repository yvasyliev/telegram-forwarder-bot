package io.github.yvasyliev.telegramforwarder.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PermalinkDeserializerTest {
    private static final String HOST = "https://www.reddit.com";
    private static final JsonDeserializer<URL> PERMALINK_DESERIALIZER = new PermalinkDeserializer(new RedditProperties(
            null,
            URI.create(HOST),
            null,
            null,
            null
    ));

    @Test
    void testDeserialize() throws IOException {
        var text = "/r/test/comments/abcd1234/test_post/";
        var p = mock(JsonParser.class);
        var expected = URI.create(HOST + text).toURL();

        when(p.getText()).thenReturn(text);

        var actual = assertDoesNotThrow(() -> PERMALINK_DESERIALIZER.deserialize(p, null));

        assertEquals(expected, actual);
    }
}
