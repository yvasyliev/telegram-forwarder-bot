package io.github.yvasyliev.forwarder.telegram.x.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RssUrlResourceTest {
    @Test
    void testCustomizeConnection() {
        var userAgent = "Test User Agent";
        var conn = mock(HttpURLConnection.class);
        var resource = new RssUrlResource(mock(), userAgent);

        assertDoesNotThrow(() -> resource.customizeConnection(conn));

        verify(conn).setRequestProperty(HttpHeaders.USER_AGENT, userAgent);
    }
}
