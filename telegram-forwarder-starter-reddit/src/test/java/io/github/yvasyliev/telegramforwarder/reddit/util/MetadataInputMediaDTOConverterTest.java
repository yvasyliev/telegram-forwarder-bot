package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataInputMediaDTOConverterTest {
    private static final boolean HAS_SPOILER = true;
    @InjectMocks private MetadataInputMediaDTOConverter metadataInputMediaDTOConverter;
    @Mock private MetadataPhotoUrlSelector photoUrlSelector;
    @Mock private Link.Metadata metadata;
    @Mock private URL url;
    @Mock private InputMediaDTO expected;

    @Test
    void shouldConvertAnimatedImage() {
        var source = new Link.Resolution(null, null, null, url, null);
        var animation = mock(InputFileDTO.class);

        when(metadata.type()).thenReturn(Link.Metadata.Type.ANIMATED_IMAGE);
        when(metadata.source()).thenReturn(source);

        try (var inputFileDTO = mockStatic(InputFileDTO.class); var inputMediaDTO = mockStatic(InputMediaDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, HAS_SPOILER)).thenReturn(animation);
            inputMediaDTO.when(() -> InputMediaDTO.animation(animation)).thenReturn(expected);

            var actual = metadataInputMediaDTOConverter.convert(metadata, HAS_SPOILER);

            assertEquals(expected, actual);
        }
    }

    @Test
    void shouldConvertImage() {
        var photo = mock(InputFileDTO.class);

        when(metadata.type()).thenReturn(Link.Metadata.Type.IMAGE);
        when(photoUrlSelector.findBestPhotoUrl(metadata)).thenReturn(url);

        try (var inputFileDTO = mockStatic(InputFileDTO.class); var inputMediaDTO = mockStatic(InputMediaDTO.class)) {
            inputFileDTO.when(() -> InputFileDTO.fromUrl(url, HAS_SPOILER)).thenReturn(photo);
            inputMediaDTO.when(() -> InputMediaDTO.photo(photo)).thenReturn(expected);

            var actual = metadataInputMediaDTOConverter.convert(metadata, HAS_SPOILER);

            assertEquals(expected, actual);
        }
    }
}
