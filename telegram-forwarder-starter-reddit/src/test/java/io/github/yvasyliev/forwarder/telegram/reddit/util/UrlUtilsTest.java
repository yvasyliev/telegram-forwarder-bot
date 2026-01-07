package io.github.yvasyliev.forwarder.telegram.reddit.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UrlUtilsTest {
    @Test
    void testOpenStream() throws IOException {
        var url = mock(URL.class);
        var expected = mock(InputStream.class);

        when(url.openStream()).thenReturn(expected);

        var actual = assertDoesNotThrow(() -> UrlUtils.openStream(url));

        assertEquals(expected, actual);
    }

    @Test
    void testGetFileName() {
        var expected = "file.txt";
        var path = "/path/to/" + expected;
        var url = mock(URL.class);

        when(url.getPath()).thenReturn(path);

        var actual = UrlUtils.getFileName(url);

        assertEquals(expected, actual);
    }
}
