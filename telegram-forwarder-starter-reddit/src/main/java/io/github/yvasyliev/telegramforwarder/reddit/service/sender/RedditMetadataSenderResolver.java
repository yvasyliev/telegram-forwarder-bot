package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;

/**
 * Resolver for selecting the appropriate Reddit metadata sender based on metadata type.
 */
@RequiredArgsConstructor
public class RedditMetadataSenderResolver {
    private final RedditMetadataSender redditAnimationMetadataSender;
    private final RedditMetadataSender redditPhotoMetadataSender;

    /**
     * Resolves the appropriate Reddit metadata sender based on the provided metadata.
     *
     * @param metadata The metadata to evaluate.
     * @return The corresponding Reddit metadata sender.
     */
    public RedditMetadataSender resolve(Link.Metadata metadata) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> redditAnimationMetadataSender;
            case IMAGE -> redditPhotoMetadataSender;
        };
    }
}
