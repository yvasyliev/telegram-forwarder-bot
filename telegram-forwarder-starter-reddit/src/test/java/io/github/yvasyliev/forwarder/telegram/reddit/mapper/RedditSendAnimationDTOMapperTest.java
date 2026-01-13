package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
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
class RedditSendAnimationDTOMapperTest {
    private static final boolean HAS_SPOILER = true;
    @InjectMocks private RedditSendAnimationDTOMapperImpl sendAnimationDTOMapper;
    @Mock private RedditInputFileDTOMapper inputFileDTOMapper;

    @Test
    void testMapNullMetadata() {
        var actual = assertDoesNotThrow(() -> sendAnimationDTOMapper.map(null, HAS_SPOILER));

        assertNull(actual);
    }

    @Test
    void testMapMetadata() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var source = mock(Link.Resolution.class);
        var gif = mock(URL.class);
        var animation = mock(InputFileDTO.class);
        var expected = new SendAnimationDTO(animation, null, HAS_SPOILER);

        when(metadata.source()).thenReturn(source);
        when(source.gif()).thenReturn(gif);
        when(inputFileDTOMapper.map(gif)).thenReturn(animation);

        var actual = assertDoesNotThrow(() -> sendAnimationDTOMapper.map(metadata, HAS_SPOILER));

        assertEquals(expected, actual);
    }

    @Test
    void testMapNullPost() {
        var actual = assertDoesNotThrow(() -> sendAnimationDTOMapper.map(null));

        assertNull(actual);
    }

    @Test
    void testMapPost() throws IOException {
        var post = mock(Link.class);
        var caption = "caption";
        var hasSpoiler = true;
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var variants = mock(Link.Variants.class);
        var mp4 = mock(Link.Variant.class);
        var source = mock(Link.Resolution.class);
        var url = mock(URL.class);
        var animation = mock(InputFileDTO.class);
        var expected = new SendAnimationDTO(animation, caption, HAS_SPOILER);

        when(post.title()).thenReturn(caption);
        when(post.isNsfw()).thenReturn(hasSpoiler);
        when(post.isNsfw()).thenReturn(HAS_SPOILER);
        when(post.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.variants()).thenReturn(variants);
        when(variants.mp4()).thenReturn(mp4);
        when(mp4.source()).thenReturn(source);
        when(source.url()).thenReturn(url);
        when(inputFileDTOMapper.map(url)).thenReturn(animation);

        var actual = assertDoesNotThrow(() -> sendAnimationDTOMapper.map(post));

        assertEquals(expected, actual);
    }

}
