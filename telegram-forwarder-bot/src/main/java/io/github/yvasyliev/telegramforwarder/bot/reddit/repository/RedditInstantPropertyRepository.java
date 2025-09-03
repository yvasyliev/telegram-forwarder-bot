package io.github.yvasyliev.telegramforwarder.bot.reddit.repository;

import io.github.yvasyliev.telegramforwarder.bot.reddit.entity.RedditInstantProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * Repository interface for managing {@link RedditInstantProperty} entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface RedditInstantPropertyRepository
        extends JpaRepository<RedditInstantProperty, RedditInstantProperty.RedditInstantPropertyName> {
    /**
     * Retrieves the value of a RedditInstantProperty by its name.
     *
     * @param name the name of the RedditInstantProperty
     * @return an {@link Optional} containing the {@link Instant} value if found, or empty if not found
     */
    @Query("select value from RedditInstantProperty where name = :name")
    Optional<Instant> getValue(RedditInstantProperty.RedditInstantPropertyName name);
}
