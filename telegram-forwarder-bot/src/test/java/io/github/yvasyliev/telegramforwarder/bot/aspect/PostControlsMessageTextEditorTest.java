package io.github.yvasyliev.telegramforwarder.bot.aspect;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsEditMessageTextProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.mapper.EditMessageTextMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControlsMessageTextEditorTest {
    private static final String COMMAND = "someCommand";
    private static final String SUPPRESSED_API_RESPONSE = "someError";
    private final CallbackQuery callbackQuery = new CallbackQuery();
    private final CommandCallbackData callbackData = new CommandCallbackData(COMMAND, null);
    @Mock private TelegramClient telegramClient;
    @Mock private EditMessageText editMessageText;
    private PostControlsMessageTextEditor postControlsMessageTextEditor;

    @BeforeEach
    void setUp() {
        var message = mock(Message.class);
        var editMessageTextProperties = mock(PostControlsEditMessageTextProperties.EditMessageTextProperties.class);
        var editMessageTextMapper = mock(EditMessageTextMapper.class);
        var postControlsEditMessageTextProperties = new PostControlsEditMessageTextProperties(
                Map.of(COMMAND, editMessageTextProperties),
                Set.of(SUPPRESSED_API_RESPONSE)
        );

        postControlsMessageTextEditor = new PostControlsMessageTextEditor(
                telegramClient,
                editMessageTextMapper,
                postControlsEditMessageTextProperties
        );

        callbackQuery.setMessage(message);

        when(editMessageTextMapper.map(message, editMessageTextProperties)).thenReturn(editMessageText);
    }

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(editMessageText);
    }

    @Test
    void shouldEditPostControlsMessageText() {
        postControlsMessageTextEditor.editPostControlsMessageText(callbackQuery, callbackData);
    }

    @Test
    void shouldHandleSuppressedException() throws TelegramApiException {
        var apiResponse = ApiResponse.builder().errorDescription(SUPPRESSED_API_RESPONSE).build();

        when(telegramClient.execute(editMessageText)).thenThrow(new TelegramApiRequestException(null, apiResponse));

        assertDoesNotThrow(() -> postControlsMessageTextEditor.editPostControlsMessageText(
                callbackQuery,
                callbackData
        ));
    }

    @Test
    void shouldHandleUnsuppressedTelegramApiException() throws TelegramApiException {
        when(telegramClient.execute(editMessageText)).thenThrow(TelegramApiException.class);

        assertDoesNotThrow(() -> postControlsMessageTextEditor.editPostControlsMessageText(
                callbackQuery,
                callbackData
        ));
    }

    @Test
    void shouldHandleUnsuppressedTelegramApiRequestException() throws TelegramApiException {
        var apiResponse = ApiResponse.builder().errorDescription("anotherError").build();

        when(telegramClient.execute(editMessageText)).thenThrow(new TelegramApiRequestException(null, apiResponse));

        assertDoesNotThrow(() -> postControlsMessageTextEditor.editPostControlsMessageText(
                callbackQuery,
                callbackData
        ));
    }
}
