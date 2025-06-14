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

@Service
@RequiredArgsConstructor
public class PhotoSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

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
