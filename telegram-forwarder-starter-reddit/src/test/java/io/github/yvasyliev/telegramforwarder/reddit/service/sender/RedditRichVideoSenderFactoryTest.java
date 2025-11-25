package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
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
class RedditRichVideoSenderFactoryTest {
    private RedditRichVideoSenderFactory richVideoSenderFactory;
    @Mock private RedditPostSender videoAnimationSender;
    @Mock private RedditPostSender urlSender;
    @Mock private Link post;

    @BeforeEach
    void setUp() {
        richVideoSenderFactory = new RedditRichVideoSenderFactory(videoAnimationSender, urlSender);
    }

    @Test
    void shouldReturnNull() {
        var actual = richVideoSenderFactory.getRedditPostSender(post);

        assertNull(actual);
    }

    @Nested
    class PreviewTests {
        @Mock private Link.Preview preview;

        @BeforeEach
        void setUp() {
            when(post.postHint()).thenReturn(Link.PostHint.RICH_VIDEO);
            when(post.preview()).thenReturn(preview);
        }

        @Test
        void shouldReturnUrlSenderWhenRedditVideoIsNull() {
            when(preview.redditVideoPreview()).thenReturn(null);
            var actual = richVideoSenderFactory.getRedditPostSender(post);

            assertEquals(urlSender, actual);
        }

        @Test
        void shouldReturnVideoAnimationSenderWhenRedditVideoIsGif() {
            shouldReturnSenderBasedOnIsGif(true, videoAnimationSender);
        }

        @Test
        void shouldReturnUrlSenderWhenRedditVideoIsNotGif() {
            shouldReturnSenderBasedOnIsGif(false, urlSender);
        }

        private void shouldReturnSenderBasedOnIsGif(boolean isGif, RedditPostSender expected) {
            var redditVideoPreview = mock(Link.RedditVideo.class);

            when(preview.redditVideoPreview()).thenReturn(redditVideoPreview);
            when(redditVideoPreview.isGif()).thenReturn(isGif);

            var actual = richVideoSenderFactory.getRedditPostSender(post);

            assertEquals(expected, actual);
        }
    }
}
