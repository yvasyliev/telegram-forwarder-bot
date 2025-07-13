package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.util.PhotoUtils;
import io.github.yvasyliev.telegramforwarderbot.service.sender.PhotoSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoMetadataForwarderTest {
    @InjectMocks private PhotoMetadataForwarder forwarder;
    @Mock private TelegramProperties telegramProperties;
    @Mock private PhotoSender sender;

    @Test
    void testForwarder() throws TelegramApiException, IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var maxPhotoDimensions = 10_000;
        var url = mock(URL.class);
        var photo = mock(InputFileDTO.class);

        when(telegramProperties.maxPhotoDimensions()).thenReturn(maxPhotoDimensions);

        try (var photoUtils = mockStatic(PhotoUtils.class); var inputFileDTO = mockStatic(InputFileDTO.class)) {
            photoUtils.when(() -> PhotoUtils.getUrl(metadata, maxPhotoDimensions)).thenReturn(url);
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, hasSpoiler)).thenReturn(photo);

            forwarder.forward(metadata, hasSpoiler);
        }

        verify(sender).sendPhoto(photo, null);
    }
}
