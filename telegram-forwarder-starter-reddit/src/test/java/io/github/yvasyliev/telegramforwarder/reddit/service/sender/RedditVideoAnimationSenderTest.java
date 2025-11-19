package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditVideoAnimationSenderTest {
    @InjectMocks private RedditVideoAnimationSender videoAnimationSender;
    @Mock private PostSender<InputFileDTO, Message> animationSender;

    @Test
    void testSed() throws TelegramApiException, IOException {
        var link = mock(Link.class);
        var redditVideoPreview = mock(Link.RedditVideo.class);
        var preview = new Link.Preview(null, null, redditVideoPreview);
        var url = mock(URL.class);
        var isNsfw = true;
        var animation = mock(InputFileDTO.class);
        var title = "Test Video Title";

        when(link.isNsfw()).thenReturn(isNsfw);
        when(link.title()).thenReturn(title);
        when(link.preview()).thenReturn(preview);
        when(redditVideoPreview.fallbackUrl()).thenReturn(url);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, isNsfw)).thenReturn(animation);

            videoAnimationSender.send(link);
        }

        verify(animationSender).send(animation, title);
    }
}
