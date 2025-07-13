package io.github.yvasyliev.telegramforwarderbot.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import io.github.yvasyliev.telegramforwarderbot.configuration.RedditProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    private static final URI HOST_URI = URI.create(HOST);
    @InjectMocks private PermalinkDeserializer converter;
    @Mock private RedditProperties redditProperties;
    @Mock private JsonParser p;

    @Test
    void shouldBuildUrl() throws IOException {
        var expected = URI.create(HOST + PATH).toURL();

        when(redditProperties.host()).thenReturn(HOST_URI);
        when(p.getText()).thenReturn(PATH);

        var actual = assertDoesNotThrow(() -> converter.deserialize(p, mock()));

        assertEquals(expected, actual);
    }
}
