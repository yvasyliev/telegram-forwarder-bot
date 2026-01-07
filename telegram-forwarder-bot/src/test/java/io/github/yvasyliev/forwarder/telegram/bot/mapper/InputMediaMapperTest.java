package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaVideoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;

import java.io.InputStream;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class InputMediaMapperTest {
    private static final InputMediaMapper INPUT_MEDIA_MAPPER = Mappers.getMapper(InputMediaMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var mediaStream = mock(InputStream.class);
        var fileName = "fileName";
        var hasSpoiler = true;
        var inputMediaPhotoDTO = new InputMediaPhotoDTO(mediaStream, fileName, hasSpoiler);
        var inputMediaVideoDTO = new InputMediaVideoDTO(mediaStream, fileName, hasSpoiler);

        var expected1 = new InputMediaPhoto(mediaStream, fileName);
        var expected2 = new InputMediaVideo(mediaStream, fileName);

        expected1.setHasSpoiler(hasSpoiler);
        expected2.setHasSpoiler(hasSpoiler);

        return Stream.of(
                arguments(null, null),
                arguments(inputMediaPhotoDTO, expected1),
                arguments(inputMediaVideoDTO, expected2)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMap(InputMediaDTO inputMediaDTO, InputMedia expected) {
        var actual = INPUT_MEDIA_MAPPER.map(inputMediaDTO);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionForUnknownSubclass() {
        var inputMediaAnimationDTO = mock(InputMediaAnimationDTO.class);

        assertThrows(IllegalArgumentException.class, () -> INPUT_MEDIA_MAPPER.map(inputMediaAnimationDTO));
    }
}
