package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.mapper.SendVideoMapper;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.SendVideoDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.core.util.CloseableSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Service for sending video files to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class VideoSender implements PostSender<CloseableSupplier<SendVideoDTO>, Message> {
    private final TelegramAdminProperties adminProperties;
    private final SendVideoMapper sendVideoMapper;
    private final TelegramClient telegramClient;

    @Override
    public Message send(CloseableSupplier<SendVideoDTO> sendVideoDTOSupplier) throws IOException, TelegramApiException {
        try (var sendVideoDTO = sendVideoDTOSupplier.get()) {
            var sendVideo = sendVideoMapper.map(sendVideoDTO, adminProperties);

            return telegramClient.execute(sendVideo);
        }
    }
}
