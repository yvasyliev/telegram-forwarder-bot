package io.github.yvasyliev.telegramforwarderbot.reddit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

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

    public enum RedditInstantPropertyName {
        LAST_CREATED
    }
}
