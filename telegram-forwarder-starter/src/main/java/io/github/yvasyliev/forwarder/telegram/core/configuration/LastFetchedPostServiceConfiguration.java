package io.github.yvasyliev.forwarder.telegram.core.configuration;

import io.github.yvasyliev.forwarder.telegram.core.repository.LastFetchedPostRepository;
import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the {@link LastFetchedPostService} bean.
 */
@Configuration
public class LastFetchedPostServiceConfiguration {
    /**
     * Creates a {@link LastFetchedPostService} bean if one is not already present in the context.
     *
     * @param lastFetchedPostRepository the repository for managing last fetched post information
     * @return a new instance of {@link LastFetchedPostService}
     */
    @Bean
    @ConditionalOnMissingBean
    public LastFetchedPostService lastFetchedPostService(LastFetchedPostRepository lastFetchedPostRepository) {
        return new LastFetchedPostService(lastFetchedPostRepository);
    }
}
