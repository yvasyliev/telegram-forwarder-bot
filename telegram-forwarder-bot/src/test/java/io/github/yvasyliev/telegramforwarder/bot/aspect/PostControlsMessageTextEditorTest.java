package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class PostControlsMessageTextEditorTest {
    private static final String COMMAND = "testCommand";
    private static final String MESSAGE_TEXT = "Test message text";
    private static final long CHAT_ID = 123L;
    private static final int MESSAGE_ID = 456;
    private static final InlineKeyboardMarkup REPLY_MARKUP = new InlineKeyboardMarkup(List.of());
    private static final EditMessageText EDIT_MESSAGE_TEXT = EditMessageText.builder()
            .chatId(CHAT_ID)
            .messageId(MESSAGE_ID)
            .text(MESSAGE_TEXT)
            .replyMarkup(REPLY_MARKUP)
            .build();
    @InjectMocks private PostControlsMessageTextEditor postControlsMessageTextEditor;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;
    @Mock private CallbackQuery callbackQuery;
    @Mock private TelegramProperties.PostControls postControls;
    @Mock private MessageIdsCommandCallbackDataDTO callbackData;

    @BeforeEach
    void setUp() {
        var button = mock(TelegramProperties.PostControls.Button.class);
        var buttons = Map.of(COMMAND, button);
        var message = mock(Message.class);

        when(telegramProperties.postControls()).thenReturn(postControls);
        when(postControls.buttons()).thenReturn(buttons);
        when(button.messageText()).thenReturn(MESSAGE_TEXT);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(CHAT_ID);
        when(message.getMessageId()).thenReturn(MESSAGE_ID);
        when(message.getReplyMarkup()).thenReturn(REPLY_MARKUP);
        when(callbackData.getCommand()).thenReturn(COMMAND);
    }

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(EDIT_MESSAGE_TEXT);
    }

    @Test
    void testEditPostControlsMessageText(CapturedOutput capturedOutput) {
        postControlsMessageTextEditor.editPostControlsMessageText(callbackQuery, callbackData);

        assertThat(capturedOutput).isEmpty();
    }

    @Nested
    class TelegramApiExceptionTests {
        private static final String IGNORED_API_RESPONSE = "Ignored test error";
        @SuppressWarnings("checkstyle:ConstantName")
        private static final TelegramApiException[] shouldLogError = {
                new TelegramApiException(),
                new TelegramApiRequestException(null, ApiResponse.builder().errorDescription("Test error").build())
        };

        @BeforeEach
        void setUp() {
            when(postControls.ignoredApiResponses()).thenReturn(Set.of(IGNORED_API_RESPONSE));
        }

        @Test
        void shouldNotLogError(CapturedOutput capturedOutput) throws TelegramApiException {
            var apiResponse = ApiResponse.builder().errorDescription(IGNORED_API_RESPONSE).build();
            var e = new TelegramApiRequestException(null, apiResponse);

            when(telegramClient.execute(EDIT_MESSAGE_TEXT)).thenThrow(e);

            postControlsMessageTextEditor.editPostControlsMessageText(callbackQuery, callbackData);

            assertThat(capturedOutput).isEmpty();
        }

        @ParameterizedTest
        @FieldSource
        void shouldLogError(TelegramApiException e, CapturedOutput capturedOutput) throws TelegramApiException {
            when(telegramClient.execute(EDIT_MESSAGE_TEXT)).thenThrow(e);

            postControlsMessageTextEditor.editPostControlsMessageText(callbackQuery, callbackData);

            assertThat(capturedOutput).contains("ERROR", e.getClass().getName());
        }
    }
}
