package io.github.yvasyliev.forwarder.telegram.bot.service.sender;

import io.github.yvasyliev.forwarder.telegram.bot.mapper.SendMessageMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

/**
 * Service for sending URLs to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class UrlSender implements PostSender<SendUrlDTO, Message> {
    private final TelegramAdminProperties adminProperties;
    private final SendMessageMapper sendMessageMapper;
    private final TelegramClient telegramClient;

    @Override
    public Message send(SendUrlDTO sendUrlDTO) throws IOException, TelegramApiException {
        var sendMessage = sendMessageMapper.map(sendUrlDTO, adminProperties);

        return telegramClient.execute(sendMessage);
    }
}
