package io.github.yvasyliev.telegramforwarder.logging.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LoggingContextMapperTest {
    private static final String TEXT = "Sample log message";
    private static final LoggingContextMapper LOGGING_CONTEXT_MAPPER = Mappers.getMapper(LoggingContextMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var expected = new Context();

        expected.setVariable("text", TEXT);

        return Stream.of(arguments(null, null), arguments(TEXT, expected));
    };

    @ParameterizedTest
    @FieldSource
    void testMap(String text, IContext expected) {
        var actual = LOGGING_CONTEXT_MAPPER.map(text);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
