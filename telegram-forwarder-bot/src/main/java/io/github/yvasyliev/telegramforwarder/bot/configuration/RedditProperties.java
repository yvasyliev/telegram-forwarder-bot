package io.github.yvasyliev.telegramforwarder.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Configuration properties for Reddit integration.
 * This class holds the necessary properties to connect to Reddit's API,
 * including the API host, subreddit, username, user agent,
 * and video downloader configuration.
 *
 * @param apiHost         Reddit API host URI
 * @param host            Reddit host URI
 * @param subreddit       Subreddit to monitor
 * @param username        Reddit username
 * @param userAgent       User agent string for Reddit API requests
 * @param videoDownloader Configuration for downloading videos from Reddit
 */
@ConfigurationProperties(prefix = "reddit")
@Validated
public record RedditProperties(
        @NotNull URI apiHost,
        @NotNull URI host,
        @NotBlank String subreddit,
        @NotBlank String username,
        @NotBlank String userAgent,
        @Validated @NotNull VideoDownloader videoDownloader
) {
    /**
     * Configuration for downloading videos from Reddit.
     * This record holds the URI of the video and the CSS selector
     * used to extract the video element from the HTML.
     *
     * @param uri         the URI of the video to download
     * @param cssSelector the CSS selector to locate the video element
     */
    public record VideoDownloader(@NotNull URI uri, @NotBlank String cssSelector) {}
}
