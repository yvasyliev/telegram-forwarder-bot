package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendPhotoMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Sends a photo to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class PhotoSender implements PostSender<CloseableSupplier<SendPhotoDTO>, Message> {
    private final TelegramAdminProperties adminProperties;
    private final SendPhotoMapper sendPhotoMapper;
    private final TelegramClient telegramClient;

    @Override
    public Message send(CloseableSupplier<SendPhotoDTO> sendPhotoDTOSupplier) throws IOException, TelegramApiException {
        try (var sendPhotoDTO = sendPhotoDTOSupplier.get()) {
            var sendPhoto = sendPhotoMapper.map(sendPhotoDTO, adminProperties);

            return telegramClient.execute(sendPhoto);
        }
    }
}
