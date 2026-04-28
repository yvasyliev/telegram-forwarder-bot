package io.github.yvasyliev.forwarder.telegram.x.service;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.service.PostForwarder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.messaging.Message;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XPostForwarderTest {
    @Mock private FeedEntryMessageSource source;
    @Mock private XPostSenderManager xPostSenderManager;
    private PostForwarder forwarder;

    @BeforeEach
    void setUp() {
        forwarder = new XPostForwarder(List.of(source), xPostSenderManager);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testForward() {
        var message = Mockito.<Message<SyndEntry>>mock();
        var syndEntry = mock(SyndEntry.class);

        when(source.receive()).thenReturn(message, (Message<SyndEntry>) null);
        when(message.getPayload()).thenReturn(syndEntry);

        forwarder.forward();

        verify(xPostSenderManager).send(syndEntry);
    }
}
