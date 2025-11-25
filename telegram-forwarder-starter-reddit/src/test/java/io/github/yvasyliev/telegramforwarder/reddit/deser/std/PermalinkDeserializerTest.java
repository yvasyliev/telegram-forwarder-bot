package io.github.yvasyliev.telegramforwarder.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermalinkDeserializerTest {
    private static final String HOST = "https://www.reddit.com";
    private static final String PATH = "/r/test/comments/12345/test_post";
    private static final PermalinkDeserializer PERMALINK_DESERIALIZER = new PermalinkDeserializer(URI.create(HOST));
    @Mock private JsonParser p;

    @Test
    void shouldBuildUrl() throws IOException {
        var expected = URI.create(HOST + PATH).toURL();

        when(p.getText()).thenReturn(PATH);

        var actual = assertDoesNotThrow(() -> PERMALINK_DESERIALIZER.deserialize(p, mock()));

        assertEquals(expected, actual);
    }
}
