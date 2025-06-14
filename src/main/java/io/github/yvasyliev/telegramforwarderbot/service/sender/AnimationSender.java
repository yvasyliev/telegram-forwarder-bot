package io.github.yvasyliev.telegramforwarderbot.service.sender;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AnimationSender {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    public Message sendAnimation(InputFileDTO animation, String caption) throws IOException, TelegramApiException {
        try (var inputStream = animation.fileSupplier().get()) {
            var sendAnimation = SendAnimation.builder()
                    .chatId(telegramProperties.adminId())
                    .animation(new InputFile(inputStream, animation.filename()))
                    .caption(caption)
                    .hasSpoiler(animation.hasSpoiler())
                    .build();
            return telegramClient.execute(sendAnimation);
        }
    }
}
