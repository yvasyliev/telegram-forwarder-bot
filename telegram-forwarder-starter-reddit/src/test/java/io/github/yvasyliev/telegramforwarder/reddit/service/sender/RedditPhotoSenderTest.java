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
class RedditPhotoSenderTest {
    @InjectMocks private RedditPhotoSender redditPhotoSender;
    @Mock private PostSender<InputFileDTO, Message> photoSender;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var url = mock(URL.class);
        var source = new Link.Resolution(url, null, null, null, null);
        var image = new Link.Preview.Image(source, null, null, null);
        var preview = new Link.Preview(List.of(image), null, null);
        var isNsfw = true;
        var photo = mock(InputFileDTO.class);
        var caption = "Test Caption";

        when(post.isNsfw()).thenReturn(isNsfw);
        when(post.title()).thenReturn(caption);
        when(post.preview()).thenReturn(preview);

        try (var inputFileDTO = mockStatic(InputFileDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, isNsfw)).thenReturn(photo);

            redditPhotoSender.send(post);
        }

        verify(photoSender).send(photo, caption);
    }
}
