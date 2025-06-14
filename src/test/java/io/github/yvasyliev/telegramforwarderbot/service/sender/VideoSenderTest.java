package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.util.InputStreamSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
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
class VideoSenderTest {
    @InjectMocks private VideoSender sender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSendVideo() throws TelegramApiException, IOException {
        var inputStream = mock(InputStream.class);
        var fileSupplier = mock(InputStreamSupplier.class);
        var adminId = 123456789L;
        var caption = "Test video caption";
        var filename = "test_video.mp4";
        var hasSpoiler = true;
        var video = new InputFileDTO(fileSupplier, filename, hasSpoiler);
        var sendVideo = SendVideo.builder()
                .chatId(adminId)
                .video(new InputFile(inputStream, filename))
                .supportsStreaming(true)
                .caption(caption)
                .hasSpoiler(hasSpoiler)
                .build();
        var expected = mock(Message.class);

        when(fileSupplier.get()).thenReturn(inputStream);
        when(telegramProperties.adminId()).thenReturn(adminId);
        when(telegramClient.execute(sendVideo)).thenReturn(expected);

        var actual = sender.sendVideo(video, caption);

        assertEquals(expected, actual);
        verify(telegramClient).execute(sendVideo);
        verify(inputStream).close();
    }
}