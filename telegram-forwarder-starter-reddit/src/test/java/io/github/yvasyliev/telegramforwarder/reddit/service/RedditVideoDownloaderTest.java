package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditVideoDownloaderTest {
    private static final String VIDEO_DOWNLOADER_URI = "https://www.videodownloader.com/api/download";
    private static final String PERMALINK = "https://www.reddit.com/r/test/comments/123456/test_post/";
    private static final String USER_AGENT = "java:io.github.yvasyliev.telegramforwarderbot:v4.0.0 (by /u/test)";
    private static final String CSS_SELECTOR = "a.video-download-link";
    @InjectMocks private RedditVideoDownloader redditVideoDownloader;
    @Mock private RedditProperties redditProperties;
    @Mock private Link post;
    @Mock private Elements elements;
    private MockedStatic<Jsoup> jsoup;

    @BeforeEach
    void setUp() throws IOException {
        jsoup = mockStatic(Jsoup.class);
        var videoDownloaderProperties = new RedditProperties.VideoDownloader(
                URI.create(VIDEO_DOWNLOADER_URI),
                CSS_SELECTOR
        );
        var connection = mock(Connection.class);
        var document = mock(Document.class);

        when(redditProperties.userAgent()).thenReturn(USER_AGENT);
        when(redditProperties.videoDownloader()).thenReturn(videoDownloaderProperties);
        when(post.permalink()).thenReturn(URI.create(PERMALINK).toURL());
        jsoup.when(() -> Jsoup.connect(VIDEO_DOWNLOADER_URI + "?url=" + PERMALINK)).thenReturn(connection);
        when(connection.header(HttpHeaders.USER_AGENT, USER_AGENT)).thenReturn(connection);
        when(connection.get()).thenReturn(document);
        when(document.select(CSS_SELECTOR)).thenReturn(elements);
    }

    @AfterEach
    void tearDown() {
        jsoup.close();
    }

    @Test
    void shouldGetVideoDownloadUrl() throws IOException {
        var element = mock(Element.class);
        var href = "https://www.example.com/video.mp4";
        var expected = URI.create(href).toURL();

        doReturn(element).when(elements).first();
        when(element.attr("href")).thenReturn(href);

        var actual = redditVideoDownloader.getVideoDownloadUrl(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowException() {
        assertThrows(IOException.class, () -> redditVideoDownloader.getVideoDownloadUrl(post));
    }
}
