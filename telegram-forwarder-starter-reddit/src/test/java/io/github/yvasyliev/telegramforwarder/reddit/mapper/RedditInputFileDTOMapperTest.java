package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditVideoDownloader;
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
class RedditInputFileDTOMapperTest {
    @InjectMocks private RedditInputFileDTOMapperImpl inputFileDTOMapper;
    @Mock private RedditVideoDownloader videoDownloader;

    @Test
    void testMapNullUrl() {
        var actual = assertDoesNotThrow(() -> inputFileDTOMapper.map((URL) null));

        assertNull(actual);
    }

    @Test
    void testMapUrl() throws IOException {
        var url = mock(URL.class);
        var mediaStream = mock(InputStream.class);
        var fileName = "testfile.mp4";
        var expected = new InputFileDTO(mediaStream, fileName);

        when(url.openStream()).thenReturn(mediaStream);
        when(url.getPath()).thenReturn("/path/to/" + fileName);

        var actual = assertDoesNotThrow(() -> inputFileDTOMapper.map(url));

        assertEquals(expected, actual);
    }

    @Test
    void testMapNullPost() {
        var actual = assertDoesNotThrow(() -> inputFileDTOMapper.map((Link) null));

        assertNull(actual);
    }

    @Test
    void testMapPost() throws IOException {
        var post = mock(Link.class);
        var url = mock(URL.class);
        var mediaStream = mock(InputStream.class);
        var postId = "abc123";
        var expected = new InputFileDTO(mediaStream, postId + ".mp4");

        when(videoDownloader.getVideoDownloadUrl(post)).thenReturn(url);
        when(url.openStream()).thenReturn(mediaStream);
        when(post.id()).thenReturn(postId);

        var actual = assertDoesNotThrow(() -> inputFileDTOMapper.map(post));

        assertEquals(expected, actual);
    }
}
