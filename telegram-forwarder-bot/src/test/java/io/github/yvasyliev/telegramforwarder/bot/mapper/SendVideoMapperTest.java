package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.SendVideoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendVideoMapperTest {
    @InjectMocks private SendVideoMapperImpl sendVideoMapper;
    @Mock private InputFileMapper inputFileMapper;

    @Test
    void testMapNull() {
        var actual = sendVideoMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var videoDTO = mock(InputFileDTO.class);
        var caption = "caption";
        var hasSpoiler = true;
        var chatId = 12345L;
        var video = mock(InputFile.class);
        var sendVideoDTO = new SendVideoDTO(videoDTO, caption, hasSpoiler);
        var adminProperties = new TelegramAdminProperties(chatId);
        var expected = SendVideo.builder()
                .chatId(chatId)
                .video(video)
                .supportsStreaming(true)
                .caption(caption)
                .hasSpoiler(hasSpoiler)
                .build();

        when(inputFileMapper.map(videoDTO)).thenReturn(video);

        var actual = sendVideoMapper.map(sendVideoDTO, adminProperties);

        assertEquals(expected, actual);
    }
}
