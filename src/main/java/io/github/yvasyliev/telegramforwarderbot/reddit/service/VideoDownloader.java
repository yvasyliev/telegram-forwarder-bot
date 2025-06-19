package io.github.yvasyliev.telegramforwarderbot.reddit.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Service for downloading videos from Reddit links.
 */
@Service
@RequiredArgsConstructor
public class VideoDownloader {
    private final RedditProperties redditProperties;

    /**
     * Downloads the video from the given Reddit link.
     *
     * @param link the Reddit link containing the video
     * @return the {@link URL} of the downloaded video
     * @throws IOException if an error occurs while downloading the video
     */
    public URL getVideoDownloadUrl(Link link) throws IOException {
        var url = UriComponentsBuilder.fromUri(redditProperties.videoDownloader().uri())
                .queryParam("url", link.permalink())
                .build()
                .toUriString();

        var downloadInfo = Jsoup.connect(url)
                .header(HttpHeaders.USER_AGENT, redditProperties.userAgent())
                .get()
                .select(redditProperties.videoDownloader().cssSelector())
                .first();

        if (downloadInfo == null) {
            throw new IOException("Video URL is not parsable. URL: %s, CSS Selector: %s".formatted(
                    url,
                    redditProperties.videoDownloader().cssSelector()
            ));
        }

        return URI.create(downloadInfo.attr("href")).toURL();
    }
}
