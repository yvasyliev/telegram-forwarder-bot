package io.github.yvasyliev.forwarder.telegram.reddit.service.sender;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendAnimationDTOMapper;
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
class RedditAnimationSenderTest {
    @InjectMocks private RedditAnimationSender redditAnimationSender;
    @Mock private RedditSendAnimationDTOMapper sendAnimationDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendAnimationDTO>, Message> animationSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendAnimationDTO>> sendAnimationDTOSupplierCaptor;

    @Test
    void testSend() throws IOException, TelegramApiException {
        var post = mock(Link.class);
        var expected = mock(SendAnimationDTO.class);

        when(sendAnimationDTOMapper.map(post)).thenReturn(expected);

        assertDoesNotThrow(() -> redditAnimationSender.send(post));

        verify(animationSender).send(sendAnimationDTOSupplierCaptor.capture());

        var actual = sendAnimationDTOSupplierCaptor.getValue();

        assertNotNull(actual);
        assertEquals(expected, actual.get());
    }
}
