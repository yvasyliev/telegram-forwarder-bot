package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.UrlSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkForwarderTest {
    @InjectMocks private LinkForwarder forwarder;
    @Mock private UrlSender sender;

    @Test
    void testForward() throws TelegramApiException {
        var link = mock(Link.class);
        var url = mock(URL.class);
        var title = "Test Title";

        when(link.url()).thenReturn(url);
        when(link.title()).thenReturn(title);

        forwarder.forward(link);

        verify(sender).sendUrl(url, title);
    }
}