package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackQueryResponseSenderTest {
    @InjectMocks private CallbackQueryResponseSender callbackQueryResponseSender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void testAnswerCallbackQuery() throws TelegramApiException {
        var callbackQueryId = "123";
        var callbackAnswerText = "Test answer";
        var command = "testCommand";
        var callbackQuery = mock(CallbackQuery.class);
        var callbackData = mock(MessageIdsCommandCallbackDataDTO.class);
        var postControls = mock(TelegramProperties.PostControls.class);
        var button = mock(TelegramProperties.PostControls.Button.class);
        var answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQueryId)
                .text(callbackAnswerText)
                .build();

        when(telegramProperties.postControls()).thenReturn(postControls);
        when(postControls.buttons()).thenReturn(Map.of(command, button));
        when(button.callbackAnswerText()).thenReturn(callbackAnswerText);
        when(callbackQuery.getId()).thenReturn(callbackQueryId);
        when(callbackData.getCommand()).thenReturn(command);

        assertDoesNotThrow(() -> callbackQueryResponseSender.answerCallbackQuery(callbackQuery, callbackData));

        verify(telegramClient).execute(answerCallbackQuery);
    }
}
