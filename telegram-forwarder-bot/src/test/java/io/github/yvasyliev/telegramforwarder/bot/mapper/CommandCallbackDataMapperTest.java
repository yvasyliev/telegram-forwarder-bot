package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommandCallbackDataMapperTest {
    private static final String COMMAND = "testCommand";
    private static final Integer MESSAGE_ID = 123;

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapCommandCallbackData = () -> {
        var callbackData1 = new CommandCallbackData(COMMAND, null);
        var callbackData2 = new CommandCallbackData(COMMAND, List.of(MESSAGE_ID));

        var expected1 = new RawCommandCallbackData(COMMAND, null, NumberUtils.INTEGER_ZERO);
        var expected2 = new RawCommandCallbackData(COMMAND, MESSAGE_ID, NumberUtils.INTEGER_ONE);

        return Stream.of(
                arguments(null, null),
                arguments(callbackData1, expected1),
                arguments(callbackData2, expected2)
        );
    };

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapRawCommandCallbackData = () -> {
        var callbackData = new RawCommandCallbackData(COMMAND, MESSAGE_ID, NumberUtils.INTEGER_ONE);
        var expected = new CommandCallbackData(COMMAND, List.of(MESSAGE_ID));

        return Stream.of(
                arguments(null, null),
                arguments(callbackData, expected)
        );
    };

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapInlineKeyboardButtonProperties = () -> {
        var ikbp = new PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties(
                COMMAND,
                null
        );
        var messages = List.of(Message.builder().messageId(MESSAGE_ID).build());


        var expected1 = new CommandCallbackData(COMMAND, null);
        var expected2 = new CommandCallbackData(null, List.of(MESSAGE_ID));
        var expected3 = new CommandCallbackData(COMMAND, List.of(MESSAGE_ID));

        return Stream.of(
                arguments(null, null, null),
                arguments(ikbp, null, expected1),
                arguments(null, messages, expected2),
                arguments(ikbp, messages, expected3)
        );
    };

    private static final CommandCallbackDataMapper COMMAND_CALLBACK_DATA_MAPPER
            = Mappers.getMapper(CommandCallbackDataMapper.class);

    @ParameterizedTest
    @FieldSource
    void testMapCommandCallbackData(CommandCallbackData callbackData, RawCommandCallbackData expected) {
        var actual = COMMAND_CALLBACK_DATA_MAPPER.map(callbackData);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testMapRawCommandCallbackData(RawCommandCallbackData callbackData, CommandCallbackData expected) {
        var actual = COMMAND_CALLBACK_DATA_MAPPER.map(callbackData);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testMapInlineKeyboardButtonProperties(
            PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties ikbp,
            List<Message> messages,
            CommandCallbackData expected
    ) {
        var actual = COMMAND_CALLBACK_DATA_MAPPER.map(ikbp, messages);

        assertEquals(expected, actual);
    }
}
