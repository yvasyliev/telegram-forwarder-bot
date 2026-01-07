package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import io.github.yvasyliev.forwarder.telegram.bot.repository.ApprovedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing approved posts.
 * Provides methods to save, poll, and delete approved posts.
 */
@Service
@RequiredArgsConstructor
public class ApprovedPostService {
    private final ApprovedPostRepository postRepository;

    /**
     * Saves an approved post with the given message IDs and caption removal flag.
     *
     * @param messageIds    the list of message IDs to associate with the approved post.
     * @param removeCaption flag indicating whether to remove the caption from the post.
     * @return the saved {@link ApprovedPost} entity.
     */
    @Transactional
    public ApprovedPost save(List<Integer> messageIds, boolean removeCaption) {
        var approvedPost = postRepository.findFirstByMessageIdsIn(messageIds).orElseGet(() -> {
            var post = new ApprovedPost();
            post.setMessageIds(messageIds);
            return post;
        });

        approvedPost.setRemoveCaption(removeCaption);

        return postRepository.save(approvedPost);
    }

    /**
     * Polls the first approved post ordered by the approval timestamp.
     * If an approved post is found, it is deleted from the repository.
     *
     * @return an {@link Optional} containing the polled approved post, or empty if none found.
     */
    @Transactional
    public Optional<ApprovedPost> poll() {
        var approvedPost = postRepository.findFirstByOrderByApprovedAt();

        approvedPost.ifPresent(postRepository::delete);

        return approvedPost;
    }

    /**
     * Deletes approved posts that contain any of the specified message IDs.
     *
     * @param messageIds a list of message IDs to search for.
     * @return a list of {@link ApprovedPost} entities that were deleted.
     */
    @Transactional
    public List<ApprovedPost> delete(List<Integer> messageIds) {
        return postRepository.deleteByMessageIdsIn(messageIds);
    }
}
