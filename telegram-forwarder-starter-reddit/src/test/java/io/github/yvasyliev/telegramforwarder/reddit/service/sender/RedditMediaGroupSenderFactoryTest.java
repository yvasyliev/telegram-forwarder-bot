package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMediaGroupSenderFactoryTest {
    @InjectMocks private RedditMediaGroupSenderFactory mediaGroupSenderFactory;
    @Mock private RedditPostSender mediaGroupSender;
    @Mock private Link post;

    @Test
    void shouldReturnMediaGroupSender() {
        when(post.hasGalleryData()).thenReturn(true);

        var actual = mediaGroupSenderFactory.getRedditPostSender(post);

        assertEquals(mediaGroupSender, actual);
    }

    @Test
    void shouldReturnNull() {
        var actual = mediaGroupSenderFactory.getRedditPostSender(post);

        assertNull(actual);
    }
}
