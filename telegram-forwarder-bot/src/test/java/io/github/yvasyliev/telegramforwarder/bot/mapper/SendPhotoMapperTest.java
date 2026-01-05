package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.SendPhotoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendPhotoMapperTest {
    @InjectMocks private SendPhotoMapperImpl sendPhotoMapper;
    @Mock private InputFileMapper inputFileMapper;

    @Test
    void testMapNull() {
        var actual = sendPhotoMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var photoDTO = mock(InputFileDTO.class);
        var caption = "caption";
        var hasSpoiler = true;
        var chatId = 12345L;
        var photo = mock(InputFile.class);
        var sendPhotoDTO = new SendPhotoDTO(photoDTO, caption, hasSpoiler);
        var adminProperties = new TelegramAdminProperties(chatId);
        var expected = SendPhoto.builder().chatId(chatId).photo(photo).caption(caption).hasSpoiler(hasSpoiler).build();

        when(inputFileMapper.map(photoDTO)).thenReturn(photo);

        var actual = sendPhotoMapper.map(sendPhotoDTO, adminProperties);

        assertEquals(expected, actual);
    }
}
