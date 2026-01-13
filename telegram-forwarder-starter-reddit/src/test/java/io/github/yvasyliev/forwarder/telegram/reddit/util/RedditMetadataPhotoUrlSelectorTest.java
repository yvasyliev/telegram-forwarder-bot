package io.github.yvasyliev.forwarder.telegram.reddit.util;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMetadataPhotoUrlSelectorTest {
    private static final int MAX_DIMENSION_SUM = 1000;
    private static final RedditMetadataPhotoUrlSelector METADATA_PHOTO_URL_SELECTOR
            = new RedditMetadataPhotoUrlSelector(MAX_DIMENSION_SUM);
    @Mock private Link.Metadata metadata;
    @Mock private URL expected;

    @Test
    void shouldReturnSourceUrlWhenWithinLimit() {
        var source = new Link.Resolution(expected, 400, 500, null, null);

        when(metadata.source()).thenReturn(source);

        var actual = METADATA_PHOTO_URL_SELECTOR.findBestPhotoUrl(metadata);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnBestResolutionUrlWhenExceedingLimit() {
        var source = new Link.Resolution(null, 600, 500, null, null);
        var resolution1 = new Link.Resolution(null, 400, 300, null, null);
        var resolution2 = new Link.Resolution(expected, 500, 400, null, null);

        when(metadata.source()).thenReturn(source);
        when(metadata.resolutions()).thenReturn(List.of(resolution1, resolution2));

        var actual = METADATA_PHOTO_URL_SELECTOR.findBestPhotoUrl(metadata);

        assertEquals(expected, actual);
    }
}
