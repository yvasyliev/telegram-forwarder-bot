package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditRichVideoSenderManagerTest {
    @Mock private RedditPostSender animationSender;
    @Mock private RedditPostSender urlSender;
    private RedditRichVideoSenderManager richVideoSenderManager;

    @BeforeEach
    void setUp() {
        richVideoSenderManager = new RedditRichVideoSenderManager(animationSender, urlSender);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock = """
                        HOSTED_VIDEO, false
                        IMAGE, false
                        LINK, false
                        RICH_VIDEO, true
                        GALLERY, false
                        , false"""
    )
    void testCanSend(Link.PostHint postHint, boolean expected) {
        var post = mock(Link.class);

        when(post.postHint()).thenReturn(postHint);

        var actual = richVideoSenderManager.canSend(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSendUrlWhenNoRedditVideoIsNull() throws TelegramApiException, IOException {
        testSend(null, urlSender);
    }

    @Test
    void shouldSendUrlWhenRedditVideoIsNotGif() throws TelegramApiException, IOException {
        var redditVideo = mock(Link.RedditVideo.class);

        when(redditVideo.isGif()).thenReturn(false);

        testSend(redditVideo, urlSender);
    }

    @Test
    void shouldSendAnimationWhenRedditVideoIsGif() throws TelegramApiException, IOException {
        var redditVideo = mock(Link.RedditVideo.class);

        when(redditVideo.isGif()).thenReturn(true);

        testSend(redditVideo, animationSender);
    }

    private void testSend(Link.RedditVideo redditVideo, RedditPostSender postSender)
            throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var preview = mock(Link.Preview.class);

        when(post.preview()).thenReturn(preview);
        when(preview.redditVideoPreview()).thenReturn(redditVideo);

        assertDoesNotThrow(() -> richVideoSenderManager.send(post));

        verify(postSender).send(post);
    }
}
