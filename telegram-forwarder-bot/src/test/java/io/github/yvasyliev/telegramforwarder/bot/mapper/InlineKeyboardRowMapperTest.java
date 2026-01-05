package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InlineKeyboardRowMapperTest {
    @InjectMocks private InlineKeyboardRowMapperImpl inlineKeyboardRowMapper;
    @Mock private InlineKeyboardButtonMapper inlineKeyboardButtonMapper;

    @Test
    void testMapNull() {
        var actual = inlineKeyboardRowMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var ikbp = mock(
                PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties.class
        );
        var messages = List.of(mock(Message.class));
        var inlineKeyboardButton = mock(InlineKeyboardButton.class);
        var expected = new InlineKeyboardRow(inlineKeyboardButton);

        when(inlineKeyboardButtonMapper.map(ikbp, messages)).thenReturn(inlineKeyboardButton);

        var actual = inlineKeyboardRowMapper.map(List.of(ikbp), messages);

        assertEquals(expected, actual);
    }
}
