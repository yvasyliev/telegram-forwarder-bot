package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendVideoDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XVideoPostSenderStrategyTest {
    @InjectMocks private XVideoPostSenderStrategy strategy;
    @Mock private XSendVideoDTOMapper sendVideoDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendVideoDTO>> sendVideoDTOCaptor;

    @ParameterizedTest
    @CsvSource(textBlock = """
                           false, false,
                           true, true
                           """)
    void testCanSend(boolean isVideo, boolean expected) {
        var description = new XDescription("title", isVideo, null);
        var actual = strategy.canSend(mock(SyndEntry.class), description);

        assertEquals(expected, actual);
    }

    @Test
    void testSend() throws IOException, TelegramApiException {
        var post = mock(SyndEntry.class);
        var description = mock(XDescription.class);
        var sendVideoDTO = mock(SendVideoDTO.class);

        when(sendVideoDTOMapper.map(post, description)).thenReturn(sendVideoDTO);

        assertDoesNotThrow(() -> strategy.send(post, description));

        verify(videoSender).send(sendVideoDTOCaptor.capture());
        assertEquals(sendVideoDTO, sendVideoDTOCaptor.getValue().get());
    }
}

