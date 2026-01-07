package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendPhotoMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoSenderTest {
    @InjectMocks private PhotoSender photoSender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendPhotoMapper sendPhotoMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var sendPhotoDTO = mock(SendPhotoDTO.class);
        var sendPhoto = mock(SendPhoto.class);

        when(sendPhotoMapper.map(sendPhotoDTO, adminProperties)).thenReturn(sendPhoto);

        assertDoesNotThrow(() -> photoSender.send(() -> sendPhotoDTO));

        verify(telegramClient).execute(sendPhoto);
    }
}
