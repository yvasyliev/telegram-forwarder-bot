package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.entity.RedditInstantProperty;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service for managing {@link RedditInstantProperty} entities.
 * This service provides methods to retrieve and save {@link RedditInstantProperty} instances.
 */
@RequiredArgsConstructor
public class RedditInstantPropertyService {
    private final RedditInstantPropertyRepository repository;

    /**
     * Retrieves the
     * {@link RedditInstantProperty.RedditInstantPropertyName#LAST_CREATED} {@link Instant} from the repository.
     * If no value is found, it returns {@link Instant#EPOCH}.
     *
     * @return the last created {@link Instant}
     */
    public Instant getLastCreated() {
        return repository.getValue(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED).orElse(Instant.EPOCH);
    }

    /**
     * Saves a new {@link RedditInstantProperty} with the name
     * {@link RedditInstantProperty.RedditInstantPropertyName#LAST_CREATED} and the provided value.
     *
     * @param value the {@link Instant} value to save
     * @return the saved {@link RedditInstantProperty}
     */
    @Transactional
    public RedditInstantProperty saveLastCreated(Instant value) {
        var instantProperty = new RedditInstantProperty();
        instantProperty.setName(RedditInstantProperty.RedditInstantPropertyName.LAST_CREATED);
        instantProperty.setValue(value);
        return repository.save(instantProperty);
    }
}
