package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RedditMetadataSenderResolverTest {
    private RedditMetadataSenderResolver metadataSenderResolver;
    @Mock private RedditMetadataSender redditAnimationMetadataSender;
    @Mock private RedditMetadataSender redditPhotoMetadataSender;

    @BeforeEach
    void setUp() {
        metadataSenderResolver = new RedditMetadataSenderResolver(
                redditAnimationMetadataSender,
                redditPhotoMetadataSender
        );
    }

    @Test
    void shouldReturnRedditAnimationMetadataSender() {
        testResolve(Link.Metadata.Type.ANIMATED_IMAGE, redditAnimationMetadataSender);
    }

    @Test
    void shouldReturnRedditPhotoMetadataSender() {
        testResolve(Link.Metadata.Type.IMAGE, redditPhotoMetadataSender);
    }

    private void testResolve(Link.Metadata.Type type, RedditMetadataSender expected) {
        var metadata = new Link.Metadata(null, type, null, null, null, null);

        var actual = metadataSenderResolver.resolve(metadata);

        assertEquals(expected, actual);
    }
}
