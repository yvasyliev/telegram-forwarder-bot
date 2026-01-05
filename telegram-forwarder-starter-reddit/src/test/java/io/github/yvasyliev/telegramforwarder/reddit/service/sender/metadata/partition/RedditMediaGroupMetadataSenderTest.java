package io.github.yvasyliev.telegramforwarder.reddit.service.sender.metadata.partition;

import io.github.yvasyliev.telegramforwarder.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.core.util.CloseableSupplier;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.mapper.RedditSendMediaGroupDTOMapper;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMediaGroupMetadataSenderTest {
    @InjectMocks private RedditMediaGroupMetadataSender mediaGroupMetadataSender;
    @Mock private RedditSendMediaGroupDTOMapper sendMediaGroupDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> mediaGroupSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendMediaGroupDTO>> sendMediaGroupDTOSupplierCaptor;

    @Test
    void testSend() throws IOException, TelegramApiException {
        var metadataPartition = List.of(mock(Link.Metadata.class), mock(Link.Metadata.class));
        var hasSpoiler = true;
        var caption = "caption";
        var expected = mock(SendMediaGroupDTO.class);

        when(sendMediaGroupDTOMapper.map(metadataPartition, hasSpoiler, caption)).thenReturn(expected);

        assertDoesNotThrow(() -> mediaGroupMetadataSender.send(metadataPartition, hasSpoiler, caption));

        verify(mediaGroupSender).send(sendMediaGroupDTOSupplierCaptor.capture());

        var actual = sendMediaGroupDTOSupplierCaptor.getValue();

        assertNotNull(actual);
        assertEquals(expected, actual.get());
    }
}
