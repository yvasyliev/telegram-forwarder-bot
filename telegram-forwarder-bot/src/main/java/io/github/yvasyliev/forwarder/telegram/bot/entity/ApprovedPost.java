package io.github.yvasyliev.forwarder.telegram.bot.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

/**
 * Represents an approved post in the system.
 * Contains a list of message IDs, a flag indicating whether to remove the caption,
 * and the timestamp when the post was approved.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "approved_posts")
@Data
public class ApprovedPost {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "approved_post_message_ids", joinColumns = @JoinColumn(name = "approved_post_id"))
    @Column(name = "message_id", unique = true, nullable = false)
    @NotEmpty
    private List<Integer> messageIds;

    @Column(name = "remove_caption", nullable = false)
    private Boolean removeCaption;

    @CreatedDate
    @Column(name = "approved_at", nullable = false)
    private Instant approvedAt;
}
