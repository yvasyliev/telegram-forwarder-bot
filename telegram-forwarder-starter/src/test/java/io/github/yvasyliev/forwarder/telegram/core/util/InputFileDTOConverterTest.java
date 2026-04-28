package io.github.yvasyliev.forwarder.telegram.core.util;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputFileDTOConverterTest {
    private static final InputFileDTOConverter CONVERTER = new InputFileDTOConverter();

    @Test
    void testConvert() throws IOException {
        var url = mock(URL.class);
        var filename = "test.png";
        @Cleanup var inputStream = mock(InputStream.class);
        @Cleanup var expected = new InputFileDTO(inputStream, filename);

        when(url.openStream()).thenReturn(inputStream);
        when(url.getPath()).thenReturn("/path/to/" + filename);

        @Cleanup var actual = assertDoesNotThrow(() -> CONVERTER.convert(url));

        assertEquals(expected, actual);
    }
}
