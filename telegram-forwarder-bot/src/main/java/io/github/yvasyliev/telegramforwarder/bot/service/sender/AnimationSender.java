package io.github.yvasyliev.telegramforwarder.bot.service.sender;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Sends animations to Telegram.
 */
@Service
@RequiredArgsConstructor
public class AnimationSender implements PostSender<InputFileDTO, Message> {
    private final TelegramProperties telegramProperties;
    private final TelegramClient telegramClient;

    /**
     * Sends an animation to the Telegram admin chat.
     *
     * @param animation the animation to send
     * @param caption   the caption for the animation, can be {@code null}
     * @return the sent message
     * @throws IOException          if there is an error reading the animation file
     * @throws TelegramApiException if there is an error sending the animation
     */
    @Override
    public Message send(InputFileDTO animation, String caption) throws IOException, TelegramApiException {
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
