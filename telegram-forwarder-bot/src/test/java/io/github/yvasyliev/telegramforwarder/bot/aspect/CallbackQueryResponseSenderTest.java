package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsAnswerCallbackQueryProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.AnswerCallbackQueryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private static final String COMMAND = "test_command";
    @Mock private AnswerCallbackQueryMapper answerCallbackQueryMapper;
    @Mock private TelegramClient telegramClient;
    @Mock private PostControlsAnswerCallbackQueryProperties.AnswerCallbackQueryProperties answerCallbackQueryProperties;
    private CallbackQueryResponseSender callbackQueryResponseSender;

    @BeforeEach
    void setUp() {
        callbackQueryResponseSender = new CallbackQueryResponseSender(
                new PostControlsAnswerCallbackQueryProperties(Map.of(COMMAND, answerCallbackQueryProperties)),
                answerCallbackQueryMapper,
                telegramClient
        );
    }

    @Test
    void testAnswerCallbackQuery() throws TelegramApiException {
        var callbackQuery = mock(CallbackQuery.class);
        var callbackData = new CommandCallbackData(COMMAND, null);
        var answerCallbackQuery = mock(AnswerCallbackQuery.class);

        when(answerCallbackQueryMapper.map(callbackQuery, answerCallbackQueryProperties))
                .thenReturn(answerCallbackQuery);

        assertDoesNotThrow(() -> callbackQueryResponseSender.answerCallbackQuery(callbackQuery, callbackData));

        verify(telegramClient).execute(answerCallbackQuery);
    }
}
