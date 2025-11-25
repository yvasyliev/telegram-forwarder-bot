package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.MetadataInputMediaDTOConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaGroupMetadataSenderStrategyTest {
    @InjectMocks private MediaGroupMetadataSenderStrategy mediaGroupMetadataSenderStrategy;
    @Mock private MetadataInputMediaDTOConverter metadataInputMediaDTOConverter;
    @Mock private PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var caption = "Test caption";
        var inputMediaDTO = mock(InputMediaDTO.class);

        when(metadataInputMediaDTOConverter.convert(metadata, hasSpoiler)).thenReturn(inputMediaDTO);

        assertDoesNotThrow(() -> mediaGroupMetadataSenderStrategy.send(List.of(metadata), hasSpoiler, caption));

        verify(mediaGroupSender).send(List.of(inputMediaDTO), caption);
    }
}
