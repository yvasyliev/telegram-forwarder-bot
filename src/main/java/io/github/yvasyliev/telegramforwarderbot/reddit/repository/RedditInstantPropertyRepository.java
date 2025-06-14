package io.github.yvasyliev.telegramforwarderbot.reddit.repository;

import io.github.yvasyliev.telegramforwarderbot.reddit.entity.RedditInstantProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RedditInstantPropertyRepository
        extends JpaRepository<RedditInstantProperty, RedditInstantProperty.RedditInstantPropertyName> {
    @Query("select value from RedditInstantProperty where name = :name")
    Optional<Instant> getValue(RedditInstantProperty.RedditInstantPropertyName name);
}
