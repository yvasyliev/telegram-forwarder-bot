package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableArrayList;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataInputMediaDTOConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditSendMediaGroupDTOMapperTest {
    private static final boolean HAS_SPOILER = true;
    private static final String CAPTION = "Sample Caption";
    @InjectMocks private RedditSendMediaGroupDTOMapperImpl sendMediaGroupDTOMapper;
    @Mock private RedditMetadataInputMediaDTOConverter metadataInputMediaDTOConverter;

    @Test
    void testMapNullMetadataPartitionAndCaption() {
        var actual = assertDoesNotThrow(() -> sendMediaGroupDTOMapper.map(null, HAS_SPOILER, null));

        assertNull(actual);
    }

    @Test
    void testMapNullMetadataPartition() {
        var expected = new SendMediaGroupDTO(null, CAPTION);

        var actual = assertDoesNotThrow(() -> sendMediaGroupDTOMapper.map(null, HAS_SPOILER, CAPTION));

        assertEquals(expected, actual);
    }

    @Test
    void testMap() throws IOException {
        var metadata = mock(Link.Metadata.class);
        var media = mock(InputMediaDTO.class);
        var expected = new SendMediaGroupDTO(new CloseableArrayList<>(), CAPTION);

        expected.medias().add(media);

        when(metadataInputMediaDTOConverter.convert(metadata, HAS_SPOILER)).thenReturn(media);

        var actual = assertDoesNotThrow(() -> sendMediaGroupDTOMapper.map(List.of(metadata), HAS_SPOILER, CAPTION));

        assertEquals(expected, actual);
    }
}
