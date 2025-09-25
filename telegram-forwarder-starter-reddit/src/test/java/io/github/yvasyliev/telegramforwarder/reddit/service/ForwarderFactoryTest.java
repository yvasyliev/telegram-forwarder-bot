package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ForwarderProvider;
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
class ForwarderFactoryTest {
    @Mock private Link link;

    @Test
    void shouldReturnForwarder() {
        var forwarderProvider = mock(ForwarderProvider.class);
        var forwarderFactory = new ForwarderFactory(List.of(forwarderProvider));
        var expected = mock(Forwarder.class);

        when(forwarderProvider.apply(link)).thenReturn(expected);

        var actual = forwarderFactory.apply(link);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNoopForwarder() {
        var forwarderFactory = new ForwarderFactory(List.of());

        var actual = forwarderFactory.apply(link);

        assertNotNull(actual);
        assertDoesNotThrow(() -> actual.forward(link));
    }
}
