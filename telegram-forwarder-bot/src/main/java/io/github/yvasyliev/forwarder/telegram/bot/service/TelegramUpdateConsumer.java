package io.github.yvasyliev.forwarder.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

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
    private final JsonMapper jsonMapper;

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

    private Supplier<JsonNode> json(Update update) {
        return () -> jsonMapper.convertValue(update, JsonNode.class);
    }
}
