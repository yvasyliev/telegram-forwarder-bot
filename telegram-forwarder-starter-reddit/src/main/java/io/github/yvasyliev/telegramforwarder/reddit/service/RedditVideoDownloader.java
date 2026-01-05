package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Service for downloading videos from Reddit post.
 */
@RequiredArgsConstructor
public class RedditVideoDownloader {
    private final URI apiUri;
    private final String userAgent;
    private final String cssSelector;

    /**
     * Downloads the video from the given Reddit post.
     *
     * @param post the Reddit post containing the video
     * @return the {@link URL} of the downloaded video
     * @throws IOException if an error occurs while downloading the video
     */
    public URL getVideoDownloadUrl(Link post) throws IOException {
        var url = UriComponentsBuilder.fromUri(apiUri)
                .queryParam("url", post.permalink())
                .build()
                .toUriString();
        var downloadInfo = Jsoup.connect(url)
                .header(HttpHeaders.USER_AGENT, userAgent)
                .get()
                .select(cssSelector)
                .first();

        if (downloadInfo != null) {
            return URI.create(downloadInfo.attr("href")).toURL();
        }

        throw new IOException("Video URL is not parsable. URL: %s, CSS Selector: %s".formatted(url, cssSelector));
    }
}
