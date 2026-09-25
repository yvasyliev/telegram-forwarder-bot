package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.PartialBotApiMethodMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.PartialBotApiMethodDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Service for sending posts to Telegram using various Bot API methods.
 */
@Service
@RequiredArgsConstructor
public class PostSender {
    private final TelegramAdminProperties adminProperties;
    private final PartialBotApiMethodMapper botApiMethodMapper;
    private final TelegramClient telegramClient;

    /**
     * Sends a post to Telegram using the provided method DTO supplier.
     *
     * @param methodDTOSupplier a supplier that provides a {@link PartialBotApiMethodDTO} to be sent
     * @return the response from the Telegram API, which can be of various types depending on the method used
     * @throws IOException          if an I/O error occurs during the sending process
     * @throws TelegramApiException if a Telegram API error occurs during the sending process
     */
    public Object send(CloseableSupplier<PartialBotApiMethodDTO> methodDTOSupplier)
            throws IOException, TelegramApiException {
        try (var methodDTO = methodDTOSupplier.get()) {
            return switch (botApiMethodMapper.map(methodDTO, adminProperties)) {
                case SendAnimation sendAnimation -> telegramClient.execute(sendAnimation);
                case SendMediaGroup sendMediaGroup -> telegramClient.execute(sendMediaGroup);
                case SendMessage sendMessage -> telegramClient.execute(sendMessage);
                case SendPhoto sendPhoto -> telegramClient.execute(sendPhoto);
                case SendVideo sendVideo -> telegramClient.execute(sendVideo);
                default -> throw new IllegalArgumentException("Unsupported Telegram Bot API method: " + methodDTO);
            };
        }
    }
}
