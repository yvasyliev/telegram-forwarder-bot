package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsEditMessageTextProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class EditMessageTextMapperTest {
    private static final String TEXT = "Sample edited message text";
    private static final int MESSAGE_ID = 12345;
    private static final long CHAT_ID = 67890L;
    private static final EditMessageTextMapper EDIT_MESSAGE_TEXT_MAPPER
            = Mappers.getMapper(EditMessageTextMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var inlineKeyboardMarkup = mock(InlineKeyboardMarkup.class);
        var chat = new Chat(CHAT_ID, "Test Chat");
        var editMessageTextProperties = new PostControlsEditMessageTextProperties.EditMessageTextProperties(TEXT);
        var message1 = Message.builder().messageId(MESSAGE_ID).chat(chat).build();
        var message2 = Message.builder()
                .messageId(MESSAGE_ID)
                .chat(chat)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        var expected1 = EditMessageText.builder().text(TEXT).build();
        var expected2 = EditMessageText.builder().chatId(CHAT_ID).messageId(MESSAGE_ID).text(TEXT).build();
        var expected3 = EditMessageText.builder()
                .chatId(CHAT_ID)
                .messageId(MESSAGE_ID)
                .replyMarkup(inlineKeyboardMarkup)
                .text(TEXT)
                .build();

        return Stream.of(
                arguments(null, null, null),
                arguments(null, editMessageTextProperties, expected1),
                arguments(message1, editMessageTextProperties, expected2),
                arguments(message2, editMessageTextProperties, expected3)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMap(
            Message message,
            PostControlsEditMessageTextProperties.EditMessageTextProperties emtp,
            EditMessageText expected
    ) {
        var actual = EDIT_MESSAGE_TEXT_MAPPER.map(message, emtp);

        assertEquals(expected, actual);
    }
}
