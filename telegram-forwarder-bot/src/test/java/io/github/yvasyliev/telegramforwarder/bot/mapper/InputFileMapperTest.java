package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.InputStream;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class InputFileMapperTest {
    private static final InputFileMapper INPUT_FILE_MAPPER = Mappers.getMapper(InputFileMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var mediaStream = mock(InputStream.class);
        var fileName = "file-name";
        var inputFile = new InputFileDTO(mediaStream, fileName);
        var expected = new InputFile(mediaStream, fileName);

        return Stream.of(arguments(null, null), arguments(inputFile, expected));
    };

    @ParameterizedTest
    @FieldSource
    void testMap(InputFileDTO inputFile, InputFile expected) {
        var actual = INPUT_FILE_MAPPER.map(inputFile);

        assertEquals(expected, actual);
    }
}
