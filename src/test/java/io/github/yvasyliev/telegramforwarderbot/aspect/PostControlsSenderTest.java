package io.github.yvasyliev.telegramforwarderbot.aspect;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.service.CallbackDataConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControlsSenderTest {
    private static final long CHAT_ID = 123L;
    private static final String INITIAL_MESSAGE_TEXT = "Initial message text";
    private static final String COMMAND = "testCommand";
    private static final int MESSAGE_ID = 456;
    private static final String CALLBACK_DATA = "callbackData";
    private static final String BUTTON_TEXT = "Button Text";
    private static final InlineKeyboardButton BUTTON = InlineKeyboardButton.builder()
            .text(BUTTON_TEXT)
            .callbackData(CALLBACK_DATA)
            .build();
    private static final ReplyKeyboard REPLY_KEYBOARD = new InlineKeyboardMarkup(List.of(
            new InlineKeyboardRow(BUTTON)
    ));
    private static final SendMessage SEND_MESSAGE = SendMessage.builder()
            .chatId(CHAT_ID)
            .text(INITIAL_MESSAGE_TEXT)
            .replyMarkup(REPLY_KEYBOARD)
            .build();
    private static final Message MESSAGE = Message.builder()
            .messageId(MESSAGE_ID)
            .chat(new Chat(CHAT_ID, "private"))
            .build();
    @InjectMocks private PostControlsSender postControlsSender;
    @Mock private TelegramProperties telegramProperties;
    @Mock private CallbackDataConverter callbackDataConverter;
    @Mock private TelegramClient telegramClient;

    @BeforeEach
    void setUp() {
        var postControls = mock(TelegramProperties.PostControls.class);
        var button = mock(TelegramProperties.PostControls.Button.class);
        var callbackDataDTO = new MessageIdsCommandCallbackDataDTO();

        callbackDataDTO.setCommand(COMMAND);
        callbackDataDTO.setMessageIds(List.of(MESSAGE_ID));

        when(telegramProperties.postControls()).thenReturn(postControls);
        when(postControls.buttons()).thenReturn(Map.of(COMMAND, button));
        when(postControls.initialMessageText()).thenReturn(INITIAL_MESSAGE_TEXT);
        when(button.buttonText()).thenReturn(BUTTON_TEXT);
        when(callbackDataConverter.toCallbackData(callbackDataDTO)).thenReturn(CALLBACK_DATA);
    }

    @AfterEach
    void tearDown() throws TelegramApiException {
        verify(telegramClient).execute(SEND_MESSAGE);
    }

    @Test
    void shouldSendKeyboardForMessage() {
        postControlsSender.sendPostControlKeyboard(MESSAGE);
    }

    @Test
    void shouldSendKeyboardForMessages() {
        postControlsSender.sendPostControlKeyboard(List.of(MESSAGE));
    }

    @Nested
    class ExceptionTests {
        @BeforeEach
        void setUp() throws TelegramApiException {
            when(telegramClient.execute(SEND_MESSAGE)).thenThrow(new TelegramApiException());
        }

        @Test
        void shouldNotThrowForMessage() {
            assertDoesNotThrow(PostControlsSenderTest.this::shouldSendKeyboardForMessage);
        }

        @Test
        void shouldNotThrowForMessages() {
            assertDoesNotThrow(PostControlsSenderTest.this::shouldSendKeyboardForMessages);
        }
    }
}