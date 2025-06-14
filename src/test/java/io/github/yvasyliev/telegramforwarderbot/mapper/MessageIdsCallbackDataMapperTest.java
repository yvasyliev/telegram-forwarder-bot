package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MessageIdsCallbackDataMapperTest {
    private static final MessageIdsCallbackDataMapper MAPPER = Mappers.getMapper(MessageIdsCallbackDataMapper.class);
    private static final String COMMAND = "testCommand";
    private static final MessageIdsCommandCallbackDataDTO MESSAGE_IDS_COMMAND_CALLBACK_DATA_DTO =
            new MessageIdsCommandCallbackDataDTO() {{
                setMessageIds(List.of(100, 101, 102, 103, 104));
                setCommand(COMMAND);
            }};
    private static final MessageIdsTelegramCommandCallbackDataDTO MESSAGE_IDS_TELEGRAM_COMMAND_CALLBACK_DATA_DTO =
            new MessageIdsTelegramCommandCallbackDataDTO() {{
                setFirstMessageId(100);
                setMessageCount(5);
                setCommand(COMMAND);
            }};
    private static final Supplier<Stream<Arguments>> testMapMessageIdsCommandCallbackDataDTO = () -> {
        var callbackData = new MessageIdsCommandCallbackDataDTO();
        var expected = new MessageIdsTelegramCommandCallbackDataDTO();

        callbackData.setCommand(COMMAND);
        expected.setMessageCount(NumberUtils.INTEGER_ZERO);
        expected.setCommand(COMMAND);

        return Stream.of(
                arguments(null, null),
                arguments(MESSAGE_IDS_COMMAND_CALLBACK_DATA_DTO, MESSAGE_IDS_TELEGRAM_COMMAND_CALLBACK_DATA_DTO),
                arguments(callbackData, expected)
        );
    };
    private static final Supplier<Stream<Arguments>> testMapMessageIdsTelegramCommandCallbackDataDTO = () -> Stream.of(
            arguments(null, null),
            arguments(MESSAGE_IDS_TELEGRAM_COMMAND_CALLBACK_DATA_DTO, MESSAGE_IDS_COMMAND_CALLBACK_DATA_DTO)
    );
    private static final Supplier<Stream<List<String>>> testGetSize =
            () -> Stream.of(List.of("a", "b", "c", "d"), List.of(), null);

    @ParameterizedTest
    @FieldSource
    void testMapMessageIdsCommandCallbackDataDTO(
            MessageIdsCommandCallbackDataDTO callbackData,
            MessageIdsTelegramCommandCallbackDataDTO expected
    ) {
        var actual = MAPPER.map(callbackData);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testMapMessageIdsTelegramCommandCallbackDataDTO(
            MessageIdsTelegramCommandCallbackDataDTO callbackData,
            MessageIdsCommandCallbackDataDTO expected
    ) {
        var actual = MAPPER.map(callbackData);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testGetSize(Collection<?> collection) {
        var expected = CollectionUtils.size(collection);

        var actual = MAPPER.getSize(collection);

        assertEquals(expected, actual);
    }

    @Test
    void mapMessageIds() {
        var callbackData = new MessageIdsTelegramCommandCallbackDataDTO();
        var expected = List.of(100, 101, 102, 103, 104);

        callbackData.setFirstMessageId(100);
        callbackData.setMessageCount(5);

        var actual = MAPPER.mapMessageIds(callbackData);

        assertEquals(expected, actual);
    }
}