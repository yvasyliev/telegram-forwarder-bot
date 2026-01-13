package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.util.CommandCallbackDataConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InlineKeyboardButtonMapperTest {
    @InjectMocks private InlineKeyboardButtonMapperImpl inlineKeyboardButtonMapper;
    @Mock private CommandCallbackDataConverter commandCallbackDataConverter;
    @Mock private CommandCallbackDataMapper commandCallbackDataMapper;

    @Test
    void testMapNull() {
        var actual = inlineKeyboardButtonMapper.map(null, null);

        assertNull(actual);
    }

    @Test
    void testMap() {
        var text = "Sample Button Text";
        var callbackData = "sample_callback_data";
        var ikbp = new PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties(
                null,
                text
        );
        var messages = List.of(mock(Message.class));
        var commandCallbackData = mock(CommandCallbackData.class);
        var expected = InlineKeyboardButton.builder().callbackData(callbackData).text(text).build();

        when(commandCallbackDataMapper.map(ikbp, messages)).thenReturn(commandCallbackData);
        when(commandCallbackDataConverter.convert(commandCallbackData)).thenReturn(callbackData);

        var actual = inlineKeyboardButtonMapper.map(ikbp, messages);

        assertEquals(expected, actual);
    }
}
