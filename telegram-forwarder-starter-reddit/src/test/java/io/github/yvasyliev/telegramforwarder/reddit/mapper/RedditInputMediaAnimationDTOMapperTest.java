package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaAnimationDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedditInputMediaAnimationDTOMapperTest {
    private static final RedditInputMediaAnimationDTOMapper INPUT_MEDIA_ANIMATION_DTO_MAPPER
            = Mappers.getMapper(RedditInputMediaAnimationDTOMapper.class);

    @Test
    void testMapNullMetadata() throws IOException {
        var actual = INPUT_MEDIA_ANIMATION_DTO_MAPPER.map(null, false);

        assertNull(actual);
    }

    @Test
    void testMap() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var mediaStream = mock(InputStream.class);
        var fileName = "animation.gif";
        var hasSpoiler = true;
        var expected = new InputMediaAnimationDTO(mediaStream, fileName, hasSpoiler);

        when(metadata.source()).thenReturn(source);
        when(source.gif()).thenReturn(url);
        when(url.openStream()).thenReturn(mediaStream);
        when(url.getPath()).thenReturn("/path/to/" + fileName);

        var actual = assertDoesNotThrow(() -> INPUT_MEDIA_ANIMATION_DTO_MAPPER.map(metadata, hasSpoiler));

        assertEquals(expected, actual);
    }
}
