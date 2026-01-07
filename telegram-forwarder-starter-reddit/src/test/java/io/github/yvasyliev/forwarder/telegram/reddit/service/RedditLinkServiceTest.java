package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Listing;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Thing;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditLinkServiceTest {
    private static final String TEST_SUBREDDIT = "testsubreddit";
    private static final Instant LAST_CREATED = Instant.now().minusSeconds(NumberUtils.INTEGER_ONE);
    @Mock private RedditInstantPropertyService instantPropertyService;
    @Mock private RedditClient redditClient;

    @Test
    void testGetNewLinks() {
        var redditLinkService = new RedditLinkService(instantPropertyService, redditClient, TEST_SUBREDDIT);
        var oldPost = mock(Link.class);
        var newPost = mock(Link.class);
        var sourcePost = mock(Link.class);
        var listing = new Listing(
                null,
                null,
                null,
                null,
                List.of(new Thing<>(null, oldPost), new Thing<>(null, newPost)),
                null
        );
        var expected = List.of(sourcePost);

        when(instantPropertyService.getLastCreated()).thenReturn(LAST_CREATED);
        when(redditClient.getSubredditNew(TEST_SUBREDDIT)).thenReturn(new Thing<>(null, listing));
        when(oldPost.created()).thenReturn(LAST_CREATED.minusSeconds(NumberUtils.INTEGER_ONE));
        when(newPost.created()).thenReturn(LAST_CREATED.plusSeconds(NumberUtils.INTEGER_ONE));
        when(newPost.sourceLink()).thenReturn(sourcePost);

        var actual = redditLinkService.getNewLinks();

        assertNotNull(actual);
        assertEquals(expected, actual.toList());
    }
}
