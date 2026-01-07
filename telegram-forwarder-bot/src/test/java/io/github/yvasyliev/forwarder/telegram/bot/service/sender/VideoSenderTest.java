package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendVideoMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoSenderTest {
    @InjectMocks private VideoSender videoSender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendVideoMapper sendVideoMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var sendVideoDTO = mock(SendVideoDTO.class);
        var sendVideo = mock(SendVideo.class);

        when(sendVideoMapper.map(sendVideoDTO, adminProperties)).thenReturn(sendVideo);

        assertDoesNotThrow(() -> videoSender.send(() -> sendVideoDTO));

        verify(telegramClient).execute(sendVideo);
    }
}
