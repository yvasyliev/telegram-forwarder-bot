package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
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
public class PhotoSender implements PostSender<InputFileDTO, Message> {
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
    @Override
    public Message send(InputFileDTO photo, String caption) throws IOException, TelegramApiException {
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
