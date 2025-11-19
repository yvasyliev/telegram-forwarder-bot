package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.factory.RedditPostSenderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPostSenderResolverTest {
    @Mock private Link link;

    @Test
    void shouldReturnForwarder() {
        var forwarderProvider = mock(RedditPostSenderFactory.class);
        var forwarderFactory = new RedditPostSenderResolver(List.of(forwarderProvider));
        var expected = mock(RedditPostSender.class);

        when(forwarderProvider.getRedditPostSender(link)).thenReturn(expected);

        var actual = forwarderFactory.resolve(link);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNoopForwarder() {
        var forwarderFactory = new RedditPostSenderResolver(List.of());

        var actual = forwarderFactory.resolve(link);

        assertNotNull(actual);
        assertDoesNotThrow(() -> actual.send(link));
    }
}
