package io.github.yvasyliev.forwarder.telegram.x.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.InputFileDTOConverter;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XSendPhotoDTOMapperTest {
    @InjectMocks private XSendPhotoDTOMapperImpl mapper;
    @Mock private InputFileDTOConverter inputFileDTOConverter;

    @Test
    void testMapNullDescription() {
        var actual = assertDoesNotThrow(() -> mapper.map(null));

        assertNull(actual);
    }

    @Test
    void testMapNullImages() {
        var title = "title";
        var description = new XDescription(title, false, null);
        var expected = new SendPhotoDTO(null, title, null);
        var actual = assertDoesNotThrow(() -> mapper.map(description));

        assertEquals(expected, actual);
    }

    @Test
    void testMap() throws IOException {
        var title = "photo title";
        var image = "http://example.com/path/photo.png";
        var inputFile = mock(InputFileDTO.class);
        var expected = new SendPhotoDTO(inputFile, title, null);

        when(inputFileDTOConverter.convert(URI.create(image).toURL())).thenReturn(inputFile);

        var actual = assertDoesNotThrow(() -> mapper.map(new XDescription(title, false, List.of(image))));

        assertEquals(expected, actual);
    }
}
