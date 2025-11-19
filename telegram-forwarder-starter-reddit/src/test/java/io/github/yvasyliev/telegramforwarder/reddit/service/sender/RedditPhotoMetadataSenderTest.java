package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.PhotoUtils;
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
class RedditPhotoMetadataSenderTest {
    @InjectMocks private RedditPhotoMetadataSender photoMetadataSender;
    @Mock private TelegramMediaProperties mediaProperties;
    @Mock private PostSender<InputFileDTO, Message> photoSender;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var maxPhotoDimensions = 10_000;
        var url = mock(URL.class);
        var photo = mock(InputFileDTO.class);

        when(mediaProperties.maxPhotoDimensions()).thenReturn(maxPhotoDimensions);

        try (var photoUtils = mockStatic(PhotoUtils.class); var inputFileDTO = mockStatic(InputFileDTO.class)) {
            photoUtils.when(() -> PhotoUtils.getUrl(metadata, maxPhotoDimensions)).thenReturn(url);
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, hasSpoiler)).thenReturn(photo);

            photoMetadataSender.send(metadata, hasSpoiler);
        }

        verify(photoSender).send(photo, null);
    }
}
