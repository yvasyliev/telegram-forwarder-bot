package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.service.sender.AnimationSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageAnimationForwarderTest {
    @InjectMocks private ImageAnimationForwarder forwarder;
    @Mock private AnimationSender sender;

    @Test
    void testForward() throws TelegramApiException, IOException {
        var link = mock(Link.class);
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var variants = mock(Link.Variants.class);
        var mp4 = mock(Link.Variant.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var hasSpoiler = false;
        var animation = mock(InputFileDTO.class);
        var title = "Test Title";

        when(link.isNsfw()).thenReturn(hasSpoiler);
        when(link.title()).thenReturn(title);
        when(link.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.variants()).thenReturn(variants);
        when(variants.mp4()).thenReturn(mp4);
        when(mp4.source()).thenReturn(source);
        when(source.url()).thenReturn(url);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, hasSpoiler)).thenReturn(animation);

            forwarder.forward(link);
        }

        verify(sender).sendAnimation(animation, title);
    }
}