package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkForwarderTest {
    @InjectMocks private LinkForwarder forwarder;
    @Mock private PostSender<URL, Message> sender;

    @Test
    void testForward() throws IOException, TelegramApiException {
        var link = mock(Link.class);
        var url = mock(URL.class);
        var title = "Test Title";

        when(link.url()).thenReturn(url);
        when(link.title()).thenReturn(title);

        forwarder.forward(link);

        verify(sender).send(url, title);
    }
}
