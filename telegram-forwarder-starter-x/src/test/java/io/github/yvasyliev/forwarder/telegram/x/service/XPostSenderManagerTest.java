package io.github.yvasyliev.forwarder.telegram.x.service;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.x.util.XDescriptionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XPostSenderManagerTest {
    @Mock private XPostSenderStrategy strategy;
    @Mock private XDescriptionParser xDescriptionParser;
    private XPostSenderManager manager;

    @BeforeEach
    void setUp() {
        manager = new XPostSenderManager(List.of(strategy), xDescriptionParser);
    }

    @Test
    void shouldSendPost() throws TelegramApiException, IOException {
        var post = mock(SyndEntry.class);
        var description = mockXDescription(post);

        when(strategy.canSend(post, description)).thenReturn(true);

        manager.send(post);

        verify(strategy).send(post, description);
    }

    @Test
    void shouldHandleNoSender() {
        var post = mock(SyndEntry.class);

        mockXDescription(post);

        assertDoesNotThrow(() -> manager.send(post));
    }

    @ParameterizedTest
    @ValueSource(classes = {TelegramApiException.class, IOException.class})
    void shouldHandleSenderExceptions(Class<Exception> e)
            throws TelegramApiException, IOException {
        var post = mock(SyndEntry.class);
        var description = mockXDescription(post);

        when(strategy.canSend(post, description)).thenReturn(true);
        doThrow(e).when(strategy).send(post, description);

        assertDoesNotThrow(() -> manager.send(post));
    }

    private XDescription mockXDescription(SyndEntry post) {
        var content = mock(SyndContent.class);
        var value = "description";
        var description = mock(XDescription.class);

        when(post.getDescription()).thenReturn(content);
        when(content.getValue()).thenReturn(value);
        when(xDescriptionParser.parse(value)).thenReturn(description);

        return description;
    }
}

