package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
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

@ExtendWith(MockitoExtension.class)
class RedditAnimationMetadataSenderTest {
    @InjectMocks private RedditAnimationMetadataSender animationMetadataSender;
    @Mock private PostSender<InputFileDTO, Message> animationSender;

    @Test
    void testSend() throws IOException, TelegramApiException {
        var hasSpoiler = false;
        var gif = mock(URL.class);
        var source = new Link.Resolution(null, null, null, gif, null);
        var metadata = new Link.Metadata(null, null, null, null, source, null);
        var animation = mock(InputFileDTO.class);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(gif, hasSpoiler)).thenReturn(animation);

            animationMetadataSender.send(metadata, hasSpoiler);
        }

        verify(animationSender).send(animation, null);
    }
}
