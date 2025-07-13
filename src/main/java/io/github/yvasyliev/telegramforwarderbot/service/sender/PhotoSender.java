package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Sends a photo to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class PhotoSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Sends a photo to the admin chat.
     *
     * @param photo   the photo to send
     * @param caption the caption for the photo
     * @return the sent message
     * @throws IOException          if an I/O error occurs
     * @throws TelegramApiException if a Telegram API error occurs
     */
    public Message sendPhoto(InputFileDTO photo, String caption) throws IOException, TelegramApiException {
        try (var inputStream = photo.fileSupplier().get()) {
            var sendPhoto = SendPhoto.builder()
                    .chatId(telegramProperties.adminId())
                    .photo(new InputFile(inputStream, photo.filename()))
                    .caption(caption)
                    .hasSpoiler(photo.hasSpoiler())
                    .build();
            return telegramClient.execute(sendPhoto);
        }
    }
}
