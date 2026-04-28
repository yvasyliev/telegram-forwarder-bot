package io.github.yvasyliev.forwarder.telegram.x.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableArrayList;
import io.github.yvasyliev.forwarder.telegram.core.util.InputMediaPhotoDTOConverter;
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
class XSendMediaGroupDTOMapperTest {
    @InjectMocks private XSendMediaGroupDTOMapperImpl mapper;
    @Mock private InputMediaPhotoDTOConverter inputMediaPhotoDTOConverter;

    @Test
    void testMapNullDescription() {
        var actual = assertDoesNotThrow(() -> mapper.map(null));

        assertNull(actual);
    }

    @Test
    void testMapNullImages() {
        var title = "title";
        var xDescription = new XDescription(title, false, null);
        var expected = new SendMediaGroupDTO(null, title);
        var actual = assertDoesNotThrow(() -> mapper.map(xDescription));

        assertEquals(expected, actual);
    }

    @Test
    void testMap() throws IOException {
        var title = "title";
        var image = "http://image";
        var inputMediaPhotoDTO = mock(InputMediaPhotoDTO.class);
        var medias = new CloseableArrayList<InputMediaDTO>();
        var expected = new SendMediaGroupDTO(medias, title);

        medias.add(inputMediaPhotoDTO);

        when(inputMediaPhotoDTOConverter.convert(URI.create(image).toURL())).thenReturn(inputMediaPhotoDTO);

        var actual = mapper.map(new XDescription(title, false, List.of(image)));

        assertEquals(expected, actual);
    }
}
