package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.SendUrlDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.mapper.RedditSendUrlDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditUrlSenderTest {
    @InjectMocks private RedditUrlSender redditUrlSender;
    @Mock private RedditSendUrlDTOMapper sendUrlDTOMapper;
    @Mock private PostSender<SendUrlDTO, Message> urlSender;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var sendUrlDTO = mock(SendUrlDTO.class);

        when(sendUrlDTOMapper.map(post)).thenReturn(sendUrlDTO);

        assertDoesNotThrow(() -> redditUrlSender.send(post));

        verify(urlSender).send(sendUrlDTO);
    }
}
