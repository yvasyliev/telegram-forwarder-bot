package io.github.yvasyliev.forwarder.telegram.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * Composite identifier for {@link LastFetchedPost}.
 */
@Embeddable
@Data
public class LastFetchedPostId {
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false)
    private SourceType sourceType;

    @Column(name = "source_name", nullable = false)
    private String sourceName;

    /**
     * Supported post sources.
     */
    public enum SourceType {
        /**
         * Reddit source.
         */
        REDDIT,

        /**
         * X (Twitter) source.
         */
        X
    }
}

