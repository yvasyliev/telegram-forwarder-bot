package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.util.InputStreamSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
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
class AnimationSenderTest {
    @InjectMocks private AnimationSender sender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws IOException, TelegramApiException {
        var adminId = 123456789L;
        var hasSpoiler = true;
        var filename = "test_animation.mp4";
        var fileSupplier = mock(InputStreamSupplier.class);
        var caption = "Test Animation";
        var inputStream = mock(InputStream.class);
        var sendAnimation = SendAnimation.builder()
                .chatId(adminId)
                .animation(new InputFile(inputStream, filename))
                .caption(caption)
                .hasSpoiler(hasSpoiler)
                .build();
        var expected = mock(Message.class);

        when(fileSupplier.get()).thenReturn(inputStream);
        when(telegramProperties.adminId()).thenReturn(adminId);
        when(telegramClient.execute(sendAnimation)).thenReturn(expected);

        var actual = sender.send(new InputFileDTO(fileSupplier, filename, hasSpoiler), caption);

        assertEquals(expected, actual);
        verify(telegramClient).execute(sendAnimation);
        verify(inputStream).close();
    }
}
