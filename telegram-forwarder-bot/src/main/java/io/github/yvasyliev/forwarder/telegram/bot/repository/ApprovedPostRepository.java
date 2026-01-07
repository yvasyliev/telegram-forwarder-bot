package io.github.yvasyliev.forwarder.telegram.bot.repository;

import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link ApprovedPost} entities.
 * Provides methods to find and delete approved posts based on message IDs.
 */
@Repository
public interface ApprovedPostRepository extends JpaRepository<ApprovedPost, Long> {
    /**
     * Finds the first approved post ordered by the approval timestamp.
     *
     * @return an {@link Optional} containing the first approved post, or empty if none found
     */
    Optional<ApprovedPost> findFirstByOrderByApprovedAt();

    /**
     * Finds the first approved post that contains any of the specified message IDs.
     *
     * @param messageIds a list of message IDs to search for
     * @return an {@link Optional} containing the first approved post with matching message IDs, or empty if none found
     */
    Optional<ApprovedPost> findFirstByMessageIdsIn(List<Integer> messageIds);

    /**
     * Deletes all approved posts that contain any of the specified message IDs.
     *
     * @param messageIds a list of message IDs to search for
     * @return a list of {@link ApprovedPost} entities that were deleted
     */
    List<ApprovedPost> deleteByMessageIdsIn(List<Integer> messageIds);
}
