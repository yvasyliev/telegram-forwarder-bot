package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendPhotoDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPhotoMetadataSenderTest {
    @InjectMocks private RedditPhotoMetadataSender photoMetadataSender;
    @Mock private RedditSendPhotoDTOMapper sendPhotoDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendPhotoDTO>> sendPhotoDTOSupplierCaptor;

    @Test
    void testSend() throws IOException, TelegramApiException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var expected = mock(SendPhotoDTO.class);

        when(sendPhotoDTOMapper.map(metadata, hasSpoiler)).thenReturn(expected);

        assertDoesNotThrow(() -> photoMetadataSender.send(metadata, hasSpoiler));

        verify(photoSender).send(sendPhotoDTOSupplierCaptor.capture());

        var actual = sendPhotoDTOSupplierCaptor.getValue();

        assertNotNull(actual);
        assertEquals(expected, actual.get());
    }
}
