package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReplyKeyboardMapperTest {
    @InjectMocks private ReplyKeyboardMapperImpl replyKeyboardMapper;
    @Mock private InlineKeyboardRowMapper inlineKeyboardRowMapper;

    @Test
    void testMapNull() {
        var actual = replyKeyboardMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var inlineKeyboardButtonPropertiesList = List.of(mock(
                PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties.class
        ));
        var inlineKeyboardMarkupProperties = new PostControlsSendMessageProperties.InlineKeyboardMarkupProperties(
                List.of(inlineKeyboardButtonPropertiesList)
        );
        var messages = List.of(mock(Message.class));
        var inlineKeyboardRow = mock(InlineKeyboardRow.class);
        var expected = new InlineKeyboardMarkup(List.of(inlineKeyboardRow));

        when(inlineKeyboardRowMapper.map(inlineKeyboardButtonPropertiesList, messages)).thenReturn(inlineKeyboardRow);

        var actual = replyKeyboardMapper.map(inlineKeyboardMarkupProperties, messages);

        assertEquals(expected, actual);
    }
}
