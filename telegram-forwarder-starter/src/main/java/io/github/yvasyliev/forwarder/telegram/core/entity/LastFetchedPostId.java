package io.github.yvasyliev.forwarder.telegram.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Composite identifier for {@link LastFetchedPost}.
 */
@Embeddable
@Data
public class LastFetchedPostId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

