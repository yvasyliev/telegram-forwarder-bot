package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.SendAnimationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SendAnimationMapperTest {
    @InjectMocks private SendAnimationMapperImpl sendAnimationMapper;
    @Mock private InputFileMapper inputFileMapper;

    @Test
    void testMapNull() {
        var actual = sendAnimationMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var animationDTO = mock(InputFileDTO.class);
        var caption = "caption";
        var hasSpoiler = true;
        var id = 123456789L;
        var sendAnimationDTO = new SendAnimationDTO(animationDTO, caption, hasSpoiler);
        var adminProperties = new TelegramAdminProperties(id);
        var animation = mock(InputFile.class);
        var expected = SendAnimation.builder()
                .chatId(id)
                .animation(animation)
                .caption(caption)
                .hasSpoiler(hasSpoiler)
                .build();

        when(inputFileMapper.map(animationDTO)).thenReturn(animation);

        var actual = sendAnimationMapper.map(sendAnimationDTO, adminProperties);

        assertEquals(expected, actual);
    }
}
