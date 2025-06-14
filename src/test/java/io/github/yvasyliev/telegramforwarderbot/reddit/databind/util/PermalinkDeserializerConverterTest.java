package io.github.yvasyliev.telegramforwarderbot.reddit.databind.util;

import io.github.yvasyliev.telegramforwarderbot.configuration.RedditProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermalinkDeserializerConverterTest {
    private static final String HOST = "https://www.reddit.com";
    private static final String PATH = "/r/test/comments/12345/test_post";
    private static final URI HOST_URI = URI.create(HOST);
    @InjectMocks private PermalinkDeserializerConverter converter;
    @Mock private RedditProperties redditProperties;

    @BeforeEach
    void setUp() {
        when(redditProperties.host()).thenReturn(HOST_URI);
    }

    @Test
    void shouldBuildUrl() throws MalformedURLException {
        var expected = URI.create(HOST + PATH).toURL();

        var actual = converter.convert(PATH);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowUncheckedException() throws MalformedURLException {
        var builder = mock(UriComponentsBuilder.class);
        var uriComponents = mock(UriComponents.class);
        var uri = mock(URI.class);

        try (var uriComponentsBuilder = mockStatic(UriComponentsBuilder.class)) {
            uriComponentsBuilder.when(() -> UriComponentsBuilder.fromUri(HOST_URI)).thenReturn(builder);
            when(builder.path(PATH)).thenReturn(builder);
            when(builder.build()).thenReturn(uriComponents);
            when(uriComponents.toUri()).thenReturn(uri);
            when(uri.toURL()).thenThrow(MalformedURLException.class);

            assertThrows(RuntimeException.class, () -> converter.convert(PATH));
        }
    }
}