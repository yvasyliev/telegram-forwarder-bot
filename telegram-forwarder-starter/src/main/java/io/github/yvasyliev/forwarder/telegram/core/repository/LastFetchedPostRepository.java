package io.github.yvasyliev.forwarder.telegram.core.repository;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

/**
 * Repository interface for managing {@link LastFetchedPost} entities.
 */
@Repository
public interface LastFetchedPostRepository extends JpaRepository<LastFetchedPost, LastFetchedPostId> {
    /**
     * Retrieves the {@code publishedAt} timestamp of the last fetched post for a given identifier.
     *
     * @param id the composite identifier of the last fetched post
     * @return an {@link Optional} containing the {@code publishedAt} timestamp if found, or empty if not found
     */
    @Query("select l.publishedAt from LastFetchedPost l where l.id = :id")
    Optional<Instant> findPublishedAtById(LastFetchedPostId id);
}
