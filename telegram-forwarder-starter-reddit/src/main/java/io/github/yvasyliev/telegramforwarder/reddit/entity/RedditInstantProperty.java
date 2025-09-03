package io.github.yvasyliev.telegramforwarder.reddit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

/**
 * Entity representing a Reddit property with an Instant value.
 *
 * <p>
 * This entity is used to store properties related to Reddit, such as the last created timestamp.
 * The property name is stored as an {@link RedditInstantPropertyName} enum, and the value is stored as an
 * {@link Instant}.
 */
@Entity
@Table(name = "reddit_timestamp_properties")
@Data
public class RedditInstantProperty {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "property_name")
    private RedditInstantPropertyName name;

    @Column(name = "property_value", nullable = false)
    private Instant value;

    /**
     * Enum representing the names of Reddit instant properties.
     *
     * <p>
     * Currently, it includes only one property: {@link RedditInstantPropertyName#LAST_CREATED}, which represents the
     * last created timestamp.
     */
    public enum RedditInstantPropertyName {
        /**
         * Represents the last created timestamp.
         */
        LAST_CREATED
    }
}
