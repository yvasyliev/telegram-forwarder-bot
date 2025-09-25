package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageForwarderProviderTest {
    private ImageForwarderProvider forwarderProvider;
    @Mock private Forwarder imageAnimationForwarder;
    @Mock private Forwarder photoForwarder;
    @Mock private Link link;

    @BeforeEach
    void setUp() {
        forwarderProvider = new ImageForwarderProvider(imageAnimationForwarder, photoForwarder);
    }

    @Test
    void shouldReturnNull() {
        var actual = forwarderProvider.apply(link);

        assertNull(actual);
    }

    @Test
    void shouldReturnImageAnimationForwarder() {
        shouldReturnForwarderBasedOnGifPresence(true, imageAnimationForwarder);
    }

    @Test
    void shouldReturnPhotoForwarder() {
        shouldReturnForwarderBasedOnGifPresence(false, photoForwarder);
    }

    private void shouldReturnForwarderBasedOnGifPresence(boolean hasGif, Forwarder expected) {
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var variants = mock(Link.Variants.class);

        when(link.postHint()).thenReturn(Link.PostHint.IMAGE);
        when(link.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.variants()).thenReturn(variants);
        when(variants.hasGif()).thenReturn(hasGif);

        var actual = forwarderProvider.apply(link);

        assertEquals(expected, actual);
    }
}
