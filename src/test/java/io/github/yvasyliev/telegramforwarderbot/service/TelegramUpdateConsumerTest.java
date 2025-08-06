package io.github.yvasyliev.telegramforwarderbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class TelegramUpdateConsumerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TelegramUpdateConsumer updateConsumer;
    @Mock private TelegramEventHandler<Message> messageHandler;
    @Mock private TelegramEventHandler<CallbackQuery> callbackQueryHandler;

    @BeforeEach
    void setUp() {
        updateConsumer = new TelegramUpdateConsumer(messageHandler, callbackQueryHandler, objectMapper);
    }

    @Test
    void shouldConsumeMessageUpdate() {
        var message = new Message();
        var update = new Update();

        update.setMessage(message);

        updateConsumer.consume(List.of(update));

        verify(messageHandler).handle(message);
    }

    @Test
    void shouldConsumeCallbackQueryUpdate() {
        var callbackQuery = new CallbackQuery();
        var update = new Update();

        update.setCallbackQuery(callbackQuery);

        updateConsumer.consume(List.of(update));

        verify(callbackQueryHandler).handle(callbackQuery);
    }

    @Test
    void shouldIgnoreUnsupportedUpdate() {
        updateConsumer.consume(List.of(new Update()));

        verifyNoInteractions(messageHandler, callbackQueryHandler);
    }

    @Test
    void shouldHandleRuntimeException() {
        var message = new Message();
        var update = new Update();

        update.setMessage(message);

        doThrow(RuntimeException.class).when(messageHandler).handle(message);

        assertDoesNotThrow(() -> updateConsumer.consume(List.of(update)));

        verify(messageHandler).handle(message);
    }

    @Test
    void shouldIgnoreJsonProcessingException() {
        var update = new Update() {
            @Override
            public Message getMessage() {
                throw new RuntimeException("Simulated exception");
            }
        };

        assertDoesNotThrow(() -> updateConsumer.consume(List.of(update)));
    }
}
