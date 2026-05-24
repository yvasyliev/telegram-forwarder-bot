package io.github.yvasyliev.forwarder.telegram.x.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Configuration properties for the X video service.
 *
 * @param uri         the URI of the X video service
 * @param xHost       the X host
 * @param cssSelector the CSS selector to use when parsing the X video service response
 */
@ConfigurationProperties("x.video-service")
@Validated
public record XVideoServiceProperties(@NotNull URI uri, @NotBlank String xHost, @NotBlank String cssSelector) {}
