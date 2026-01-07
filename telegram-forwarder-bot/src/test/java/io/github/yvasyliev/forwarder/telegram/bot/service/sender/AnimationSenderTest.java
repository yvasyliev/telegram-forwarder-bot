package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendAnimationMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimationSenderTest {
    @InjectMocks private AnimationSender animationSender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendAnimationMapper sendAnimationMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var sendAnimationDTO = mock(SendAnimationDTO.class);
        var sendAnimation = mock(SendAnimation.class);

        when(sendAnimationMapper.map(sendAnimationDTO, adminProperties)).thenReturn(sendAnimation);

        assertDoesNotThrow(() -> animationSender.send(() -> sendAnimationDTO));

        verify(telegramClient).execute(sendAnimation);
    }
}
