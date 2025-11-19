package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * Auto-configuration for Reddit integration.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "reddit", name = "enabled", havingValue = BooleanUtils.TRUE, matchIfMissing = true)
@Import({
        RedditClientConfiguration.class,
        RedditJpaConfiguration.class,
        RedditMetadataSenderConfiguration.class,
        RedditPostSenderConfiguration.class,
        RedditPostSenderFactoryConfiguration.class,
        RedditServiceConfiguration.class
})
public class RedditAutoConfiguration {}
