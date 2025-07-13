package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.util.InputStreamSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoSenderTest {
    @InjectMocks private PhotoSender sender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSendPhoto() throws IOException, TelegramApiException {
        var inputStream = mock(InputStream.class);
        var fileSupplier = mock(InputStreamSupplier.class);
        var adminId = 123456789L;
        var filename = "test_photo.jpg";
        var caption = "Test photo caption";
        var hasSpoiler = true;
        var photo = new InputFileDTO(fileSupplier, filename, hasSpoiler);
        var sendPhoto = SendPhoto.builder()
                .chatId(adminId)
                .photo(new InputFile(inputStream, filename))
                .caption(caption)
                .hasSpoiler(hasSpoiler)
                .build();
        var expected = mock(Message.class);

        when(fileSupplier.get()).thenReturn(inputStream);
        when(telegramProperties.adminId()).thenReturn(adminId);
        when(telegramClient.execute(sendPhoto)).thenReturn(expected);

        var actual = sender.sendPhoto(photo, caption);

        assertEquals(expected, actual);
        verify(telegramClient).execute(sendPhoto);
        verify(inputStream).close();
    }
}
