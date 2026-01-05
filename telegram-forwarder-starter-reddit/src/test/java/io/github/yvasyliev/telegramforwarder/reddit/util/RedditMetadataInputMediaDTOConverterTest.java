package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaAnimationDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.mapper.RedditInputMediaAnimationDTOMapper;
import io.github.yvasyliev.telegramforwarder.reddit.mapper.RedditInputMediaPhotoDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMetadataInputMediaDTOConverterTest {
    @InjectMocks private RedditMetadataInputMediaDTOConverter metadataInputMediaDTOConverter;
    @Mock private RedditInputMediaAnimationDTOMapper inputMediaAnimationDTOMapper;
    @Mock private RedditInputMediaPhotoDTOMapper inputMediaPhotoDTOMapper;

    @Test
    void shouldReturnInputMediaAnimationDTO() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var expected = mock(InputMediaAnimationDTO.class);

        when(metadata.type()).thenReturn(Link.Metadata.Type.ANIMATED_IMAGE);
        when(inputMediaAnimationDTOMapper.map(metadata, hasSpoiler)).thenReturn(expected);

        var actual = assertDoesNotThrow(() -> metadataInputMediaDTOConverter.convert(metadata, hasSpoiler));

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnInputMediaPhotoDTO() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var expected = mock(InputMediaPhotoDTO.class);

        when(metadata.type()).thenReturn(Link.Metadata.Type.IMAGE);
        when(inputMediaPhotoDTOMapper.map(metadata, hasSpoiler)).thenReturn(expected);

        var actual = assertDoesNotThrow(() -> metadataInputMediaDTOConverter.convert(metadata, hasSpoiler));

        assertEquals(expected, actual);
    }
}
