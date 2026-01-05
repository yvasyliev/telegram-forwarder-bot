package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsAnswerCallbackQueryProperties;
import io.github.yvasyliev.telegramforwarder.bot.configuration.UnauthorizedActionProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AnswerCallbackQueryMapperTest {
    private static final String ID = "id";
    private static final String TEXT = "text";
    private static final AnswerCallbackQueryMapper ANSWER_CALLBACK_QUERY_MAPPER
            = Mappers.getMapper(AnswerCallbackQueryMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapAnswerCallbackQueryProperties = () -> {
        var callbackQuery = new CallbackQuery();
        var answerCallbackQueryProperties
                = new PostControlsAnswerCallbackQueryProperties.AnswerCallbackQueryProperties(TEXT);

        callbackQuery.setId(ID);

        var expected1 = AnswerCallbackQuery.builder().callbackQueryId(ID).build();
        var expected2 = AnswerCallbackQuery.builder().callbackQueryId(ID).text(TEXT).build();

        return Stream.of(
                arguments(null, null, null),
                arguments(callbackQuery, null, expected1),
                arguments(callbackQuery, answerCallbackQueryProperties, expected2)
        );
    };

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapUnauthorizedActionProperties = () -> {
        var callbackQuery = new CallbackQuery();
        var unauthorizedActionProperties = new UnauthorizedActionProperties(TEXT);

        callbackQuery.setId(ID);

        var expected1 = AnswerCallbackQuery.builder().callbackQueryId(ID).build();
        var expected2 = AnswerCallbackQuery.builder().callbackQueryId(ID).text(TEXT).build();

        return Stream.of(
                arguments(null, null, null),
                arguments(callbackQuery, null, expected1),
                arguments(callbackQuery, unauthorizedActionProperties, expected2)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMapAnswerCallbackQueryProperties(
            CallbackQuery callbackQuery,
            PostControlsAnswerCallbackQueryProperties.AnswerCallbackQueryProperties answerCallbackQueryProperties,
            AnswerCallbackQuery expected
    ) {
        var actual = ANSWER_CALLBACK_QUERY_MAPPER.map(callbackQuery, answerCallbackQueryProperties);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testMapUnauthorizedActionProperties(
            CallbackQuery callbackQuery,
            UnauthorizedActionProperties unauthorizedActionProperties,
            AnswerCallbackQuery expected
    ) {
        var actual = ANSWER_CALLBACK_QUERY_MAPPER.map(callbackQuery, unauthorizedActionProperties);

        assertEquals(expected, actual);
    }
}
