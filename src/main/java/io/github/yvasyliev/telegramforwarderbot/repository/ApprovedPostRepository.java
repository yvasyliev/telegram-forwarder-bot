package io.github.yvasyliev.telegramforwarderbot.repository;

import io.github.yvasyliev.telegramforwarderbot.entity.ApprovedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovedPostRepository extends JpaRepository<ApprovedPost, Long> {
    Optional<ApprovedPost> findFirstByOrderByApprovedAt();

    Optional<ApprovedPost> findFirstByMessageIdsIn(List<Integer> messageIds);

    List<ApprovedPost> deleteByMessageIdsIn(List<Integer> messageIds);
}
