package io.github.yvasyliev.telegramforwarderbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;
import java.util.function.Supplier;

/**
 * Service for consuming updates from Telegram and handling them accordingly.
 * It processes incoming updates, such as messages and callback queries, and delegates
 * the handling to the appropriate event handlers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramUpdateConsumer implements LongPollingUpdateConsumer {
    private final TelegramEventHandler<Message> messageHandler;
    private final TelegramEventHandler<CallbackQuery> callbackQueryHandler;
    private final ObjectMapper objectMapper;

    @Override
    @Async
    public void consume(List<Update> updates) {
        updates.forEach(this::consume);
    }

    private void consume(Update update) {
        try {
            if (update.hasMessage()) {
                messageHandler.handle(update.getMessage());
            } else if (update.hasCallbackQuery()) {
                callbackQueryHandler.handle(update.getCallbackQuery());
            } else {
                log.atWarn().addArgument(json(update)).log("Received unsupported update: {}");
            }
        } catch (Exception e) {
            log.atError().addArgument(json(update)).log("Failed to process update: {}", e);
        }
    }

    private Supplier<?> json(Object object) {
        return () -> {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize object.", e);
                return "<unable to serialize>";
            }
        };
    }
}
