package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaVideoDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
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

class RedditInputMediaVideoDTOMapperTest {
    private static final RedditInputMediaVideoDTOMapper INPUT_MEDIA_VIDEO_DTO_MAPPER
            = Mappers.getMapper(RedditInputMediaVideoDTOMapper.class);

    @Test
    void testMapNullMetadata() throws IOException {
        var actual = INPUT_MEDIA_VIDEO_DTO_MAPPER.map(null, false);

        assertNull(actual);
    }

    @Test
    void testMap() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var mediaStream = mock(InputStream.class);
        var fileName = "video.mp4";
        var hasSpoiler = true;
        var expected = new InputMediaVideoDTO(mediaStream, fileName, hasSpoiler);

        when(metadata.source()).thenReturn(source);
        when(source.mp4()).thenReturn(url);
        when(url.openStream()).thenReturn(mediaStream);
        when(url.getPath()).thenReturn("/path/to/" + fileName);

        var actual = assertDoesNotThrow(() -> INPUT_MEDIA_VIDEO_DTO_MAPPER.map(metadata, hasSpoiler));

        assertEquals(expected, actual);
    }
}
