package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RichVideoForwarderProviderTest {
    private RichVideoForwarderProvider richVideoForwarderProvider;
    @Mock private LinkForwarder videoAnimationForwarder;
    @Mock private LinkForwarder linkForwarder;
    @Mock private Link link;

    @BeforeEach
    void setUp() {
        richVideoForwarderProvider = new RichVideoForwarderProvider(videoAnimationForwarder, linkForwarder);
    }

    @Test
    void shouldReturnNull() {
        var actual = richVideoForwarderProvider.apply(link);

        assertNull(actual);
    }

    @Nested
    class PreviewTests {
        @Mock private Link.Preview preview;

        @BeforeEach
        void setUp() {
            when(link.postHint()).thenReturn(Link.PostHint.RICH_VIDEO);
            when(link.preview()).thenReturn(preview);
        }

        @Test
        void shouldReturnLinkForwarderWhenRedditVideoIsNull() {
            when(preview.redditVideoPreview()).thenReturn(null);
            var actual = richVideoForwarderProvider.apply(link);

            assertEquals(linkForwarder, actual);
        }

        @Test
        void shouldReturnVideoAnimationForwarderWhenRedditVideoIsGif() {
            shouldReturnForwarderBasedOnIsGif(true, videoAnimationForwarder);
        }

        @Test
        void shouldReturnLinkForwarderWhenRedditVideoIsNotGif() {
            shouldReturnForwarderBasedOnIsGif(false, linkForwarder);
        }

        private void shouldReturnForwarderBasedOnIsGif(boolean isGif, LinkForwarder expected) {
            var redditVideoPreview = mock(Link.RedditVideo.class);

            when(preview.redditVideoPreview()).thenReturn(redditVideoPreview);
            when(redditVideoPreview.isGif()).thenReturn(isGif);

            var actual = richVideoForwarderProvider.apply(link);

            assertEquals(expected, actual);
        }
    }
}
