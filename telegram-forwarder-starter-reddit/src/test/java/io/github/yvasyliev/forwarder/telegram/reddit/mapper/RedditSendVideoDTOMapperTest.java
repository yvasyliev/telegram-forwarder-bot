package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditSendVideoDTOMapperTest {
    @InjectMocks private RedditSendVideoDTOMapperImpl sendVideoDTOMapper;
    @Mock private RedditInputFileDTOMapper inputFileDTOMapper;

    @Test
    void testMapNull() {
        var actual = assertDoesNotThrow(() -> sendVideoDTOMapper.map(null));

        assertNull(actual);
    }

    @Test
    void testMap() throws IOException {
        var post = mock(Link.class);
        var video = mock(InputFileDTO.class);
        var caption = "caption";
        var hasSpoiler = true;
        var expected = new SendVideoDTO(video, caption, hasSpoiler);

        when(inputFileDTOMapper.map(post)).thenReturn(video);
        when(post.title()).thenReturn(caption);
        when(post.isNsfw()).thenReturn(hasSpoiler);

        var actual = assertDoesNotThrow(() -> sendVideoDTOMapper.map(post));

        assertEquals(expected, actual);
    }
}
