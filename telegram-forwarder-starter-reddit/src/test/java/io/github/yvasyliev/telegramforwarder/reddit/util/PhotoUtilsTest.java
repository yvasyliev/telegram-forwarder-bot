package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoUtilsTest {
    private static final int MAX_PHOTO_DIMENSIONS = 300;
    @Mock private Link.Metadata metadata;
    private URL expected;

    @BeforeEach
    void setUp() throws MalformedURLException {
        expected = createUrl("https://example.com/photo.jpg");
    }

    @Test
    void shouldGetSourceUrl() {
        var source = createResolution(expected, 100, 200);

        when(metadata.source()).thenReturn(source);

        var actual = PhotoUtils.getUrl(metadata, MAX_PHOTO_DIMENSIONS);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetMaxResolutionUrl() throws MalformedURLException {
        var source = createResolution("https://example.com/photo_source.jpg", 200, 200);
        var minResolution = createResolution("https://example.com/photo_min.jpg", 100, 100);
        var maxResolution = createResolution(expected, 150, 150);

        when(metadata.source()).thenReturn(source);
        when(metadata.resolutions()).thenReturn(List.of(minResolution, maxResolution));

        var actual = PhotoUtils.getUrl(metadata, MAX_PHOTO_DIMENSIONS);

        assertEquals(expected, actual);
    }

    private Link.Resolution createResolution(String url, int width, int height) throws MalformedURLException {
        return createResolution(createUrl(url), width, height);
    }

    private Link.Resolution createResolution(URL url, int width, int height) {
        return new Link.Resolution(url, width, height, null, null);
    }

    private URL createUrl(String url) throws MalformedURLException {
        return URI.create(url).toURL();
    }
}
