package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.SendUrlDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class ContextMapperTest {
    private static final String TEXT = "text";
    private static final ContextMapper CONTEXT_MAPPER = Mappers.getMapper(ContextMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapSendUrlDTO = () -> {
        var url = mock(URL.class);
        var sendUrlDTO = new SendUrlDTO(url, TEXT);
        var expected = new Context();

        expected.setVariable("text", TEXT);
        expected.setVariable("url", url);

        return Stream.of(arguments(null, null), arguments(sendUrlDTO, expected));
    };

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapString = () -> Stream.of(
            arguments(null, null),
            arguments(TEXT, new Context())
    );

    @ParameterizedTest
    @FieldSource
    void testMapSendUrlDTO(SendUrlDTO sendUrlDTO, IContext expected) {
        var actual = CONTEXT_MAPPER.map(sendUrlDTO);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @FieldSource
    void testMapString(String str, IContext expected) {
        var actual = CONTEXT_MAPPER.map(str);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
