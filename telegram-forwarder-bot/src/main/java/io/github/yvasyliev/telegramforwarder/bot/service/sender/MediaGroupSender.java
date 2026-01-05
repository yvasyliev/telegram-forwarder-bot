package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMediaGroupMapper;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.core.util.CloseableSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.List;

/**
 * Service for sending media groups to Telegram.
 * This service allows sending multiple media files (photos, videos, animations) in a single message.
 */
@Service
@RequiredArgsConstructor
public class MediaGroupSender implements PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> {
    private final TelegramAdminProperties adminProperties;
    private final SendMediaGroupMapper sendMediaGroupMapper;
    private final TelegramClient telegramClient;

    @Override
    public List<Message> send(CloseableSupplier<SendMediaGroupDTO> sendMediaGroupDTOSupplier)
            throws IOException, TelegramApiException {
        try (var sendMediaGroupDTO = sendMediaGroupDTOSupplier.get()) {
            var sendMediaGroup = sendMediaGroupMapper.map(sendMediaGroupDTO, adminProperties);

            return telegramClient.execute(sendMediaGroup);
        }
    }
}
