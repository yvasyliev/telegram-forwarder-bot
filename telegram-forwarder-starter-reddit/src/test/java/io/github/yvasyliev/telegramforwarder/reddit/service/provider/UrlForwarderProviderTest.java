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
class UrlForwarderProviderTest {
    @InjectMocks private UrlForwarderProvider forwarderProvider;
    @Mock private LinkForwarder linkForwarder;
    @Mock private Link link;

    @Test
    void shouldReturnLinkForwarder() {
        when(link.postHint()).thenReturn(Link.PostHint.LINK);

        var actual = forwarderProvider.apply(link);

        assertEquals(linkForwarder, actual);
    }

    @Test
    void shouldReturnNull() {
        var actual = forwarderProvider.apply(link);

        assertNull(actual);
    }
}
