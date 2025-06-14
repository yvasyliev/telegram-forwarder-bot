package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.entity.ApprovedPost;
import io.github.yvasyliev.telegramforwarderbot.repository.ApprovedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovedPostService {
    private final ApprovedPostRepository postRepository;

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

    @Transactional
    public Optional<ApprovedPost> poll() {
        var approvedPost = postRepository.findFirstByOrderByApprovedAt();

        approvedPost.ifPresent(postRepository::delete);

        return approvedPost;
    }

    @Transactional
    public List<ApprovedPost> delete(List<Integer> messageIds) {
        return postRepository.deleteByMessageIdsIn(messageIds);
    }
}
