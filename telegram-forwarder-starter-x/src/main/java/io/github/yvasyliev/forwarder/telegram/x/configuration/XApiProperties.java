package io.github.yvasyliev.forwarder.telegram.x.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.jsoup.helper.HttpConnection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * Configuration properties for X API integration.
 *
 * @param uriTemplate   the URI template for the X API endpoint
 * @param userAgent     the {@code User-Agent} header to use when making requests to the X API
 * @param bytesToFilter the set of bytes to filter from the response when fetching posts from the X API
 */
@ConfigurationProperties("x.api")
@Validated
public record XApiProperties(
        @NotBlank String uriTemplate,
        @NotBlank @DefaultValue(HttpConnection.DEFAULT_UA) String userAgent,
        @NotEmpty Set<Byte> bytesToFilter
) {}
