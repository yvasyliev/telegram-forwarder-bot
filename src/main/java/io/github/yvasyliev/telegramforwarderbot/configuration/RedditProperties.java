package io.github.yvasyliev.telegramforwarderbot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

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
    public record VideoDownloader(@NotNull URI uri, @NotBlank String cssSelector) {}
}
