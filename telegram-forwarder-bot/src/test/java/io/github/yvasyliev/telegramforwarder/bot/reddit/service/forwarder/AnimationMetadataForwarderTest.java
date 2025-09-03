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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimationMetadataForwarderTest {
    @InjectMocks private AnimationMetadataForwarder forwarder;
    @Mock private PostSender<InputFileDTO, Message> sender;

    @Test
    void testForward() throws IOException, TelegramApiException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = false;
        var source = mock(Link.Resolution.class);
        var gif = mock(URL.class);
        var animation = mock(InputFileDTO.class);

        when(metadata.source()).thenReturn(source);
        when(source.gif()).thenReturn(gif);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(gif, hasSpoiler)).thenReturn(animation);

            forwarder.forward(metadata, hasSpoiler);
        }

        verify(sender).send(animation, null);
    }
}
