package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.reddit.configuration.RedditProperties;
import io.github.yvasyliev.forwarder.telegram.reddit.configuration.RedditVideoDownloaderProperties;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Service for downloading videos from Reddit post.
 */
@Service
@RequiredArgsConstructor
public class RedditVideoDownloader {
    private final RedditVideoDownloaderProperties videoDownloaderProperties;
    private final RedditProperties redditProperties;

    /**
     * Downloads the video from the given Reddit post.
     *
     * @param post the Reddit post containing the video
     * @return the {@link URL} of the downloaded video
     * @throws IOException if an error occurs while downloading the video
     */
    public URL getVideoDownloadUrl(Link post) throws IOException {
        var cssSelector = videoDownloaderProperties.cssSelector();
        var url = UriComponentsBuilder.fromUri(videoDownloaderProperties.uri())
                .queryParam("url", post.permalink())
                .build()
                .toUriString();
        var downloadInfo = Jsoup.connect(url)
                .userAgent(redditProperties.userAgent())
                .get()
                .select(cssSelector)
                .first();

        if (downloadInfo != null) {
            return URI.create(downloadInfo.attr("href")).toURL();
        }

        throw new IOException("Video URL is not parsable. URL: %s, CSS Selector: %s".formatted(url, cssSelector));
    }
}
