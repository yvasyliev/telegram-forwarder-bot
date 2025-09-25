package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForwarderFactoryTest {
    private ForwarderFactory forwarderFactory;
    @Mock private Forwarder mediaGroupForwarder;
    @Mock private Forwarder videoForwarder;
    @Mock private Forwarder imageAnimationForwarder;
    @Mock private Forwarder photoForwarder;
    @Mock private Forwarder linkForwarder;
    @Mock private Forwarder videoAnimationForwarder;
    @Mock private Link link;

    @BeforeEach
    void setUp() {
        forwarderFactory = new ForwarderFactory(
                mediaGroupForwarder,
                videoForwarder,
                imageAnimationForwarder,
                photoForwarder,
                linkForwarder,
                videoAnimationForwarder
        );
    }

    @Test
    void shouldReturnMediaGroupForwarderWhenLinkHasGalleryData() {
        when(link.hasGalleryData()).thenReturn(true);

        var actual = forwarderFactory.apply(link);

        assertEquals(mediaGroupForwarder, actual);
    }

    @Test
    void shouldReturnNoopForwarderWhenLinkHasNoPostHint() {
        var actual = forwarderFactory.apply(link);

        assertThatActualIsNoopForwarder(actual);
    }

    private void assertThatActualIsNoopForwarder(Forwarder actual) {
        assertNotNull(actual);
        assertThat(actual).isNotIn(
                mediaGroupForwarder,
                videoForwarder,
                imageAnimationForwarder,
                photoForwarder,
                linkForwarder,
                videoAnimationForwarder
        );
        assertDoesNotThrow(() -> actual.forward(link));
    }

    @Nested
    class PostHintTests {
        @BeforeEach
        void setUp() {
            when(link.hasPostHint()).thenReturn(true);
        }

        @Test
        void shouldReturnVideoForwarderWhenLinkPostHintIsHostedVideo() {
            when(link.postHint()).thenReturn(Link.PostHint.HOSTED_VIDEO);

            var actual = forwarderFactory.apply(link);

            assertEquals(videoForwarder, actual);
        }

        @Test
        void shouldReturnImageAnimationForwarderWhenLinkPostHintIsImageAndHasGifVariant() {
            shouldReturnForwarderWhenLinkPostHintIsImageBasedOnHasGifVariantProperty(true, imageAnimationForwarder);
        }

        @Test
        void shouldReturnPhotoForwarderWhenLinkPostHintIsImageAndHasNoGifVariant() {
            shouldReturnForwarderWhenLinkPostHintIsImageBasedOnHasGifVariantProperty(false, photoForwarder);
        }

        @Test
        void shouldReturnLinkForwarderWhenLinkPostHintIsLink() {
            when(link.postHint()).thenReturn(Link.PostHint.LINK);

            var actual = forwarderFactory.apply(link);

            assertEquals(linkForwarder, actual);
        }

        @Test
        void shouldReturnNoopForwarderWhenLinkPostHintIsUnhandled() {
            when(link.postHint()).thenReturn(Link.PostHint.GALLERY);

            var actual = forwarderFactory.apply(link);

            assertThatActualIsNoopForwarder(actual);
        }

        private void shouldReturnForwarderWhenLinkPostHintIsImageBasedOnHasGifVariantProperty(
                boolean hasGif,
                Forwarder expected
        ) {
            var preview = mock(Link.Preview.class);
            var image = mock(Link.Preview.Image.class);
            var variants = mock(Link.Variants.class);

            when(link.postHint()).thenReturn(Link.PostHint.IMAGE);
            when(link.preview()).thenReturn(preview);
            when(preview.images()).thenReturn(List.of(image));
            when(image.variants()).thenReturn(variants);
            when(variants.hasGif()).thenReturn(hasGif);

            var actual = forwarderFactory.apply(link);

            assertEquals(expected, actual);
        }

        @Nested
        class RichVideoTests {
            @Mock private Link.Preview preview;

            @BeforeEach
            void setUp() {
                when(link.postHint()).thenReturn(Link.PostHint.RICH_VIDEO);
                when(link.preview()).thenReturn(preview);
            }

            @Test
            void shouldReturnLinkForwarderWhenLinkPostHintIsRichVideoAndRedditVideoIsNull() {
                var actual = forwarderFactory.apply(link);

                assertEquals(linkForwarder, actual);
            }

            @Test
            void shouldReturnVideoAnimationForwarderWhenLinkPostHintIsRichVideoAndRedditVideoIsGif() {
                shouldReturnForwarderBasedOnRedditVideoGifProperty(true, videoAnimationForwarder);
            }

            @Test
            void shouldReturnLinkForwarderWhenLinkPostHintIsRichVideoAndRedditVideoIsNotGif() {
                shouldReturnForwarderBasedOnRedditVideoGifProperty(false, linkForwarder);
            }

            private void shouldReturnForwarderBasedOnRedditVideoGifProperty(boolean isGif, Forwarder expected) {
                var redditVideo = mock(Link.RedditVideo.class);

                when(preview.redditVideoPreview()).thenReturn(redditVideo);
                when(redditVideo.isGif()).thenReturn(isGif);

                var actual = forwarderFactory.apply(link);

                assertEquals(expected, actual);
            }
        }
    }
}
