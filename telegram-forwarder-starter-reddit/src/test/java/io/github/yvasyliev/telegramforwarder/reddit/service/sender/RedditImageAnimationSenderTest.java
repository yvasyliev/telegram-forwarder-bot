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
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditImageAnimationSenderTest {
    @InjectMocks private RedditImageAnimationSender imageAnimationSender;
    @Mock private PostSender<InputFileDTO, Message> animationSender;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var url = mock(URL.class);
        var source = new Link.Resolution(url, null, null, null, null);
        var mp4 = new Link.Variant(source, null);
        var variants = new Link.Variants(null, null, mp4, null, null);
        var image = new Link.Preview.Image(null, null, variants, null);
        var preview = new Link.Preview(List.of(image), null, null);
        var hasSpoiler = false;
        var animation = mock(InputFileDTO.class);
        var title = "Test Title";

        when(post.isNsfw()).thenReturn(hasSpoiler);
        when(post.title()).thenReturn(title);
        when(post.preview()).thenReturn(preview);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, hasSpoiler)).thenReturn(animation);

            imageAnimationSender.send(post);
        }

        verify(animationSender).send(animation, title);
    }
}
