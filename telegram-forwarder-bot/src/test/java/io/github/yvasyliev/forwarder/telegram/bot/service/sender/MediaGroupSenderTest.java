package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMediaGroupMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaGroupSenderTest {
    @InjectMocks private MediaGroupSender mediaGroupSender;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private SendMediaGroupMapper sendMediaGroupMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void testSend() throws TelegramApiException {
        var sendMediaGroupDTO = mock(SendMediaGroupDTO.class);
        var sendMediaGroup = mock(SendMediaGroup.class);

        when(sendMediaGroupMapper.map(sendMediaGroupDTO, adminProperties)).thenReturn(sendMediaGroup);

        assertDoesNotThrow(() -> mediaGroupSender.send(() -> sendMediaGroupDTO));

        verify(telegramClient).execute(sendMediaGroup);
    }
}
