package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Reddit video downloader configuration properties.
 *
 * @param uri         the URI of the Reddit video downloader service
 * @param cssSelector the CSS selector used to locate video elements
 */
@ConfigurationProperties("reddit.video-downloader")
@Validated
public record RedditVideoDownloaderProperties(@NotNull URI uri, @NotBlank String cssSelector) {}
