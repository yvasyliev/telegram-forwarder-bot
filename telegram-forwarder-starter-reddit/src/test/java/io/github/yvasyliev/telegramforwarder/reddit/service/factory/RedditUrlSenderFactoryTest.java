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
class RedditUrlSenderFactoryTest {
    @InjectMocks private RedditUrlSenderFactory urlSenderFactory;
    @Mock private RedditPostSender urlSender;
    @Mock private Link post;

    @Test
    void shouldReturnUrlSender() {
        when(post.postHint()).thenReturn(Link.PostHint.LINK);

        var actual = urlSenderFactory.getRedditPostSender(post);

        assertEquals(urlSender, actual);
    }

    @Test
    void shouldReturnNull() {
        var actual = urlSenderFactory.getRedditPostSender(post);

        assertNull(actual);
    }
}
