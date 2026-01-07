package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendAnimationMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Sends animations to Telegram.
 */
@Service
@RequiredArgsConstructor
public class AnimationSender implements PostSender<CloseableSupplier<SendAnimationDTO>, Message> {
    private final TelegramAdminProperties adminProperties;
    private final SendAnimationMapper sendAnimationMapper;
    private final TelegramClient telegramClient;

    @Override
    public Message send(CloseableSupplier<SendAnimationDTO> sendAnimationDTOSupplier)
            throws IOException, TelegramApiException {
        try (var sendAnimationDTO = sendAnimationDTOSupplier.get()) {
            var sendAnimation = sendAnimationMapper.map(sendAnimationDTO, adminProperties);

            return telegramClient.execute(sendAnimation);
        }
    }
}
