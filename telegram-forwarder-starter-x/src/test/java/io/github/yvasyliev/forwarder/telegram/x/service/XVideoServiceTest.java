package io.github.yvasyliev.forwarder.telegram.x.service;

import lombok.Cleanup;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class XVideoServiceTest {
    private static final URI API_URI = URI.create("https://api.xvideo.com");
    private static final String X_HOST = "x-host";
    private static final String CSS_SELECTOR = "a";
    private static final XVideoService SERVICE = new XVideoService(API_URI, X_HOST, CSS_SELECTOR);

    @Test
    void testGetVideo() throws IOException {
        var link = "https://x.com/abc";
        var url = API_URI + "?url=https://" + X_HOST + "/abc";
        var connection = mock(Connection.class);
        var document = Jsoup.parse("<a href=\"https://api.download?video=xyz\"/>");
        var expected = URI.create("https://api.download?video=xyz").toURL();
        @Cleanup var jsoup = mockStatic(Jsoup.class);

        jsoup.when(() -> Jsoup.connect(url)).thenReturn(connection);
        when(connection.get()).thenReturn(document);

        var actual = assertDoesNotThrow(() -> SERVICE.getVideoUrl(link));

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIOExceptionIfVideoUrlIsNotParsable() throws IOException {
        var link = "https://x.com/abc";
        var url = API_URI + "?url=https://" + X_HOST + "/abc";
        var connection = mock(Connection.class);
        var document = Jsoup.parse("<br/>");
        @Cleanup var jsoup = mockStatic(Jsoup.class);

        jsoup.when(() -> Jsoup.connect(url)).thenReturn(connection);
        when(connection.get()).thenReturn(document);

        assertThrows(IOException.class, () -> SERVICE.getVideoUrl(link));
    }
}
