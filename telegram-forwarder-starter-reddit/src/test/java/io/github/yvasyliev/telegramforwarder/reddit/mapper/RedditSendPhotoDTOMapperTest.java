package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.SendPhotoDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.RedditMetadataPhotoUrlSelector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditSendPhotoDTOMapperTest {
    private static final boolean HAS_SPOILER = true;
    private static final String CAPTION = "Sample Caption";
    @InjectMocks private RedditSendPhotoDTOMapperImpl sendPhotoDTOMapper;
    @Mock private RedditMetadataPhotoUrlSelector metadataPhotoUrlSelector;
    @Mock private RedditInputFileDTOMapper inputFileDTOMapper;

    @Test
    void testMapNullMetadata() {
        var actual = assertDoesNotThrow(() -> sendPhotoDTOMapper.map(null, HAS_SPOILER));

        assertNull(actual);
    }

    @Test
    void testMapMetadata() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var url = mock(URL.class);
        var photo = mock(InputFileDTO.class);
        var expected = new SendPhotoDTO(photo, null, HAS_SPOILER);

        when(metadataPhotoUrlSelector.findBestPhotoUrl(metadata)).thenReturn(url);
        when(inputFileDTOMapper.map(url)).thenReturn(photo);

        var actual = assertDoesNotThrow(() -> sendPhotoDTOMapper.map(metadata, HAS_SPOILER));

        assertEquals(expected, actual);
    }

    @Test
    void testMapNullPost() {
        var actual = assertDoesNotThrow(() -> sendPhotoDTOMapper.map(null));

        assertNull(actual);
    }

    @Test
    void testMapPost() throws IOException {
        var post = mock(Link.class);
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var photo = mock(InputFileDTO.class);
        var expected = new SendPhotoDTO(photo, CAPTION, HAS_SPOILER);

        when(post.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.source()).thenReturn(source);
        when(source.url()).thenReturn(url);
        when(post.title()).thenReturn(CAPTION);
        when(post.isNsfw()).thenReturn(HAS_SPOILER);
        when(inputFileDTOMapper.map(url)).thenReturn(photo);

        var actual = assertDoesNotThrow(() -> sendPhotoDTOMapper.map(post));

        assertEquals(expected, actual);
    }
}
