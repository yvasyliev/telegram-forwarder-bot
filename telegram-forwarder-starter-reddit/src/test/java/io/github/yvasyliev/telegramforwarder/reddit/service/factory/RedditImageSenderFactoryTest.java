package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
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
class RedditImageSenderFactoryTest {
    private RedditImageSenderFactory imageSenderFactory;
    @Mock private RedditPostSender imageAnimationSender;
    @Mock private RedditPostSender photoSender;
    @Mock private Link post;

    @BeforeEach
    void setUp() {
        imageSenderFactory = new RedditImageSenderFactory(imageAnimationSender, photoSender);
    }

    @Test
    void shouldReturnNull() {
        var actual = imageSenderFactory.getRedditPostSender(post);

        assertNull(actual);
    }

    @Test
    void shouldReturnImageAnimationSender() {
        shouldReturnSenderBasedOnGifPresence(true, imageAnimationSender);
    }

    @Test
    void shouldReturnPhotoSender() {
        shouldReturnSenderBasedOnGifPresence(false, photoSender);
    }

    private void shouldReturnSenderBasedOnGifPresence(boolean hasGif, RedditPostSender expected) {
        var variants = mock(Link.Variants.class);
        var image = new Link.Preview.Image(null, null, variants, null);
        var preview = new Link.Preview(List.of(image), null, null);

        when(post.postHint()).thenReturn(Link.PostHint.IMAGE);
        when(post.preview()).thenReturn(preview);
        when(variants.hasGif()).thenReturn(hasGif);

        var actual = imageSenderFactory.getRedditPostSender(post);

        assertEquals(expected, actual);
    }
}
