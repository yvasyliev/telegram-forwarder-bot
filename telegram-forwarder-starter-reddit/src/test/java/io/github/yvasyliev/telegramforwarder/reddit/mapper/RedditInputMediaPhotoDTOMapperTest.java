package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.RedditMetadataPhotoUrlSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditInputMediaPhotoDTOMapperTest {
    @InjectMocks private RedditInputMediaPhotoDTOMapperImpl inputMediaPhotoDTOMapper;
    @Mock private RedditMetadataPhotoUrlSelector metadataPhotoUrlSelector;

    @Test
    void testMapNull() {
        var actual = assertDoesNotThrow(() -> inputMediaPhotoDTOMapper.map(null, false));

        assertNull(actual);
    }

    @Test
    void testMap() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var url = mock(URL.class);
        var mediaStream = mock(InputStream.class);
        var fileName = "fileName.jpg";
        var expected = new InputMediaPhotoDTO(mediaStream, fileName, hasSpoiler);

        when(metadataPhotoUrlSelector.findBestPhotoUrl(metadata)).thenReturn(url);
        when(url.openStream()).thenReturn(mediaStream);
        when(url.getPath()).thenReturn("/path/to/" + fileName);

        var actual = assertDoesNotThrow(() -> inputMediaPhotoDTOMapper.map(metadata, hasSpoiler));

        assertEquals(expected, actual);
    }
}
