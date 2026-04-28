package io.github.yvasyliev.forwarder.telegram.x.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * Configuration properties for the X integration.
 *
 * @param profiles the set of X profiles to use for forwarding posts
 */
@ConfigurationProperties("x")
@Validated
public record XProperties(@NotEmpty Set<String> profiles) {}
