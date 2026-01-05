package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.telegramforwarder.core.util.CloseableArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMediaGroupMapperTest {
    @InjectMocks private SendMediaGroupMapperImpl sendMediaGroupMapper;
    @Mock private InputMediaMapper inputMediaMapper;

    @Test
    void testMapNull() {
        var actual = sendMediaGroupMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var inputMediaDTO = mock(InputMediaDTO.class);
        var caption = "caption";
        var medias = new CloseableArrayList<InputMediaDTO>();
        var inputMedia = mock(InputMedia.class);
        var id = 123456789L;
        var sendMediaGroupDTO = new SendMediaGroupDTO(medias, caption);
        var telegramAdminProperties = new TelegramAdminProperties(id);
        var expected = SendMediaGroup.builder().chatId(id).media(inputMedia).build();

        medias.add(inputMediaDTO);

        when(inputMediaMapper.map(inputMediaDTO)).thenReturn(inputMedia);

        var actual = sendMediaGroupMapper.map(sendMediaGroupDTO, telegramAdminProperties);

        assertEquals(expected, actual);
        verify(inputMedia).setCaption(caption);
    }
}
