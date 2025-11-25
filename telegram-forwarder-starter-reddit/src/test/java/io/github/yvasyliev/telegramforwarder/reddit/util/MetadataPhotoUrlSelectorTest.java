package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataPhotoUrlSelectorTest {
    private static final int MAX_DIMENSION_SUM = 1000;
    private static final MetadataPhotoUrlSelector METADATA_PHOTO_URL_SELECTOR = new MetadataPhotoUrlSelector(
            MAX_DIMENSION_SUM
    );
    @Mock private Link.Metadata metadata;
    @Mock private URL expected;

    @AfterEach
    void tearDown() {
        var actual = METADATA_PHOTO_URL_SELECTOR.findBestPhotoUrl(metadata);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSourceUrlWhenWithinLimit() {
        var source = new Link.Resolution(expected, 400, 500, null, null);

        when(metadata.source()).thenReturn(source);
    }

    @Test
    void shouldReturnBestResolutionUrlWhenExceedingLimit() {
        var source = new Link.Resolution(null, 600, 500, null, null);
        var resolution1 = new Link.Resolution(null, 400, 300, null, null);
        var resolution2 = new Link.Resolution(expected, 500, 400, null, null);

        when(metadata.source()).thenReturn(source);
        when(metadata.resolutions()).thenReturn(List.of(resolution1, resolution2));
    }
}
