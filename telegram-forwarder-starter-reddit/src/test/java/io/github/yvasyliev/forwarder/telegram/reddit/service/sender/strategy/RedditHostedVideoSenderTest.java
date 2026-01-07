package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendVideoDTOMapper;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditHostedVideoSenderTest {
    @InjectMocks private RedditHostedVideoSender hostedVideoSender;
    @Mock private RedditSendVideoDTOMapper sendVideoDTOMapper;
    @Mock private PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender;
    @Captor private ArgumentCaptor<CloseableSupplier<SendVideoDTO>> sendVideoDTOSupplierCaptor;

    @ParameterizedTest
    @CsvSource(
            textBlock = """
                        HOSTED_VIDEO, true
                        IMAGE, false
                        LINK, false
                        RICH_VIDEO, false
                        GALLERY, false
                        , false"""
    )
    void testCanSend(Link.PostHint postHint, boolean expected) {
        var post = mock(Link.class);

        when(post.postHint()).thenReturn(postHint);

        var actual = hostedVideoSender.canSend(post);

        assertEquals(expected, actual);
    }

    @Test
    void testSend() throws IOException, TelegramApiException {
        var post = mock(Link.class);
        var expected = mock(SendVideoDTO.class);

        when(sendVideoDTOMapper.map(post)).thenReturn(expected);

        assertDoesNotThrow(() -> hostedVideoSender.send(post));

        verify(videoSender).send(sendVideoDTOSupplierCaptor.capture());

        var actual = sendVideoDTOSupplierCaptor.getValue();

        assertNotNull(actual);
        assertEquals(expected, actual.get());
    }
}
