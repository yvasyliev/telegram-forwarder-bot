package io.github.yvasyliev.telegramforwarder.reddit.service.factory;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditHostedVideoSenderFactoryTest {
    @InjectMocks private RedditHostedVideoSenderFactory hostedVideoSenderFactory;
    @Mock private RedditPostSender videoSender;
    @Mock private Link post;

    @Test
    void shouldReturnVideoSender() {
        when(post.postHint()).thenReturn(Link.PostHint.HOSTED_VIDEO);

        var actual = hostedVideoSenderFactory.getRedditPostSender(post);

        assertEquals(videoSender, actual);
    }

    @Test
    void shouldReturnNull() {
        var actual = hostedVideoSenderFactory.getRedditPostSender(post);

        assertNull(actual);
    }
}
