package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendPhotoDTOMapper;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XPhotoPostSenderStrategyTest {
    @InjectMocks private XPhotoPostSenderStrategy strategy;
    @Mock private XSendPhotoDTOMapper sendPhotoDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendPhotoDTO>> sendPhotoDTOCaptor;

    @ParameterizedTest
    @CsvSource(textBlock = """
                           0, false,
                           1, true
                           2, false,
                           10, false
                           """)
    void testCanSend(int size, boolean expected) {
        var images = IntStream.range(0, size).mapToObj(_ -> StringUtils.EMPTY).toList();
        var description = new XDescription("title", false, images);
        var actual = strategy.canSend(mock(), description);

        assertEquals(expected, actual);
    }

    @Test
    void testSend() throws IOException, TelegramApiException {
        var description = mock(XDescription.class);
        var sendPhotoDTO = mock(SendPhotoDTO.class);

        when(sendPhotoDTOMapper.map(description)).thenReturn(sendPhotoDTO);

        assertDoesNotThrow(() -> strategy.send(mock(), description));

        verify(photoSender).send(sendPhotoDTOCaptor.capture());
        assertEquals(sendPhotoDTO, sendPhotoDTOCaptor.getValue().get());
    }
}
