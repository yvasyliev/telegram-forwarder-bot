package io.github.yvasyliev.forwarder.telegram.x.mapper;

import com.rometools.rome.feed.synd.SyndEntryImpl;
import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.InputFileDTOConverter;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.service.XVideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XSendVideoDTOMapperTest {
    @InjectMocks private XSendVideoDTOMapperImpl mapper;
    @Mock private XVideoService xVideoService;
    @Mock private InputFileDTOConverter inputFileDTOConverter;

    @Test
    void testMapNullParameters() {
        var actual = assertDoesNotThrow(() -> mapper.map(null, null));

        assertNull(actual);
    }

    @Test
    void testMapNullPost() {
        var title = "title";
        var expected = new SendVideoDTO(null, title, null);
        var actual = assertDoesNotThrow(() -> mapper.map(null, new XDescription(title, true, null)));

        assertEquals(expected, actual);
    }

    @Test
    void testMapNullDescription() throws IOException {
        var post = new SyndEntryImpl();
        var link = "link";
        var url = mock(URL.class);
        var video = mock(InputFileDTO.class);
        var expected = new SendVideoDTO(video, null, null);

        post.setLink(link);

        when(xVideoService.getVideoUrl(link)).thenReturn(url);
        when(inputFileDTOConverter.convert(url)).thenReturn(video);

        var actual = assertDoesNotThrow(() -> mapper.map(post, null));

        assertEquals(expected, actual);
    }

    @Test
    void testMap() throws IOException {
        var post = new SyndEntryImpl();
        var link = "link";
        var title = "title";
        var url = mock(URL.class);
        var video = mock(InputFileDTO.class);
        var expected = new SendVideoDTO(video, title, null);

        post.setLink(link);

        when(xVideoService.getVideoUrl(link)).thenReturn(url);
        when(inputFileDTOConverter.convert(url)).thenReturn(video);

        var actual = assertDoesNotThrow(() -> mapper.map(post, new XDescription(title, true, null)));

        assertEquals(expected, actual);
    }
}
