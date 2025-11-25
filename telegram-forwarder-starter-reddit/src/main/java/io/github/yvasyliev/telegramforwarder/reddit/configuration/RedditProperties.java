package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Reddit configuration properties.
 *
 * @param apiHost   Reddit API host
 * @param host      Reddit host
 * @param subreddit subreddit name
 * @param username  developer username
 * @param userAgent User-Agent HTTP header
 */
@ConfigurationProperties(prefix = "reddit")
@Validated
public record RedditProperties(
        @NotNull URI apiHost,
        @NotNull URI host,
        @NotBlank String subreddit,
        @NotBlank String username,
        @NotBlank String userAgent
) {
}
