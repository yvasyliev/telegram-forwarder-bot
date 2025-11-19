package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for JPA repositories and entity scanning related to Reddit.
 */
@Configuration
@EnableJpaRepositories("io.github.yvasyliev.telegramforwarder.reddit.repository")
@EntityScan("io.github.yvasyliev.telegramforwarder.reddit.entity")
public class RedditJpaConfiguration {}
