package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HostedVideoForwarderProviderTest {
    @InjectMocks private HostedVideoForwarderProvider forwarderProvider;
    @Mock private LinkForwarder videoForwarder;
    @Mock private Link link;

    @Test
    void shouldReturnVideoForwarder() {
        when(link.postHint()).thenReturn(Link.PostHint.HOSTED_VIDEO);

        var actual = forwarderProvider.apply(link);

        assertEquals(videoForwarder, actual);
    }

    @Test
    void shouldReturnNull() {
        var actual = forwarderProvider.apply(link);

        assertNull(actual);
    }
}
