package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.LinkForwarderProvider;
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
class LinkForwarderFactoryTest {
    @Mock private Link link;

    @Test
    void shouldReturnForwarder() {
        var forwarderProvider = mock(LinkForwarderProvider.class);
        var forwarderFactory = new LinkForwarderFactory(List.of(forwarderProvider));
        var expected = mock(LinkForwarder.class);

        when(forwarderProvider.apply(link)).thenReturn(expected);

        var actual = forwarderFactory.apply(link);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNoopForwarder() {
        var forwarderFactory = new LinkForwarderFactory(List.of());

        var actual = forwarderFactory.apply(link);

        assertNotNull(actual);
        assertDoesNotThrow(() -> actual.forward(link));
    }
}
