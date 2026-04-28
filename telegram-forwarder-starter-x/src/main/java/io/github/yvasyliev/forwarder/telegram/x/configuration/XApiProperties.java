package io.github.yvasyliev.forwarder.telegram.x.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for X API integration.
 *
 * @param uriTemplate the URI template for the X API endpoint
 * @param userAgent   the {@code User-Agent} header to use when making requests to the X API
 */
@ConfigurationProperties("x.api")
@Validated
public record XApiProperties(@NotBlank String uriTemplate, @NotBlank String userAgent) {}
