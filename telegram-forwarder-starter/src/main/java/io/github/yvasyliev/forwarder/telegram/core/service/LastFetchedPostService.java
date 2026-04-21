package io.github.yvasyliev.forwarder.telegram.core.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import io.github.yvasyliev.forwarder.telegram.core.repository.LastFetchedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service for managing the last fetched post information for different sources.
 */
@RequiredArgsConstructor
public class LastFetchedPostService {
    private final LastFetchedPostRepository lastFetchedPostRepository;

    /**
     * Retrieves the {@code publishedAt} timestamp of the last fetched post for a given source identifier.
     *
     * @param id the composite identifier of the last fetched post
     * @return the {@code publishedAt} timestamp if found, or {@link Instant#EPOCH} if not found
     */
    public Instant findPublishedAtById(LastFetchedPostId id) {
        return lastFetchedPostRepository.findPublishedAtById(id).orElse(Instant.EPOCH);
    }

    /**
     * Saves the last fetched post information to the database.
     *
     * @param lastFetchedPost the {@link LastFetchedPost} entity containing the information to be saved
     * @return the saved {@link LastFetchedPost} entity
     */
    @Transactional
    public LastFetchedPost save(LastFetchedPost lastFetchedPost) {
        return lastFetchedPostRepository.save(lastFetchedPost);
    }
}
