package io.github.yvasyliev.forwarder.telegram.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

/**
 * A last fetched post for a specific source type and name.
 */
@Entity
@Table(name = "last_fetched_posts")
@Data
public class LastFetchedPost {
    @EmbeddedId
    private LastFetchedPostId id;

    @Column(name = "published_at", nullable = false)
    private Instant publishedAt;
}

