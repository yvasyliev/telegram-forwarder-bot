package io.github.yvasyliev.telegramforwarder.reddit.service.sender.strategy;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditImageSenderManagerTest {
    @Mock private RedditPostSender redditAnimationSender;
    @Mock private RedditPostSender redditPhotoSender;
    private RedditPostSenderStrategy imageSenderManager;

    @BeforeEach
    void setUp() {
        imageSenderManager = new RedditImageSenderManager(redditAnimationSender, redditPhotoSender);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock = """
                        HOSTED_VIDEO, false
                        IMAGE, true
                        LINK, false
                        RICH_VIDEO, false
                        GALLERY, false
                        , false"""
    )
    void testCanSend(Link.PostHint postHint, boolean expected) {
        var post = mock(Link.class);

        when(post.postHint()).thenReturn(postHint);

        var actual = imageSenderManager.canSend(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSendAnimation() throws TelegramApiException, IOException {
        testSend(true, redditAnimationSender);
    }

    @Test
    void shouldSendPhoto() throws TelegramApiException, IOException {
        testSend(false, redditPhotoSender);
    }

    private void testSend(boolean hasGif, RedditPostSender postSender) throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var variants = mock(Link.Variants.class);

        when(post.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.variants()).thenReturn(variants);
        when(variants.hasGif()).thenReturn(hasGif);

        assertDoesNotThrow(() -> imageSenderManager.send(post));

        verify(postSender).send(post);
    }
}
