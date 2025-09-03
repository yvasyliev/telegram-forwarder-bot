package io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
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
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoForwarderTest {
    @InjectMocks private PhotoForwarder forwarder;
    @Mock private PostSender<InputFileDTO, Message> sender;

    @Test
    void testForward() throws TelegramApiException, IOException {
        var link = mock(Link.class);
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var isNsfw = true;
        var photo = mock(InputFileDTO.class);
        var caption = "Test Caption";

        when(link.isNsfw()).thenReturn(isNsfw);
        when(link.title()).thenReturn(caption);
        when(link.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.source()).thenReturn(source);
        when(source.url()).thenReturn(url);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, isNsfw)).thenReturn(photo);

            forwarder.forward(link);
        }

        verify(sender).send(photo, caption);
    }
}
