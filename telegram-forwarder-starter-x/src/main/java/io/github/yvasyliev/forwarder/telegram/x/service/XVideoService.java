package io.github.yvasyliev.forwarder.telegram.x.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Service class to retrieve the video URL from an X post.
 */
@RequiredArgsConstructor
public class XVideoService {
    private final URI apiUri;
    private final String xHost;
    private final String cssSelector;

    /**
     * Retrieves the video URL from the given X post link by making a request to the API and parsing the response using
     * the specified CSS selector.
     *
     * @param link the link to the X post
     * @return the video URL extracted from the X post
     * @throws IOException if an I/O error occurs while retrieving or parsing the video URL
     */
    public URL getVideoUrl(String link) throws IOException {
        var postUrl = UriComponentsBuilder.fromUriString(link)
                .host(xHost)
                .toUriString();
        var url = UriComponentsBuilder.fromUri(apiUri)
                .queryParam("url", postUrl)
                .toUriString();
        var anchor = Jsoup.connect(url).get().selectFirst(cssSelector);

        if (anchor != null) {
            return URI.create(anchor.attr("href")).toURL();
        }

        throw new IOException("Video URL is not parsable. URL: %s, CSS Selector: %s".formatted(url, cssSelector));
    }
}
