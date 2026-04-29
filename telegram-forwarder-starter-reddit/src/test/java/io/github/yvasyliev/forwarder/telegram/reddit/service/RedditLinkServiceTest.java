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
    @Mock private RedditLastFetchedPostService redditLastFetchedPostService;
    @Mock private RedditClient redditClient;

    @Test
    void testGetNewLinks() {
        var subreddit = "subreddit";
        var publishedAt = Instant.now().minusSeconds(NumberUtils.INTEGER_ONE);
        var redditLinkService = new RedditLinkService(redditLastFetchedPostService, redditClient, subreddit);
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

        when(redditLastFetchedPostService.getLastPublishedAt(subreddit)).thenReturn(publishedAt);
        when(redditClient.getSubredditNew(subreddit)).thenReturn(new Thing<>(null, listing));
        when(oldPost.created()).thenReturn(publishedAt.minusSeconds(NumberUtils.INTEGER_ONE));
        when(newPost.created()).thenReturn(publishedAt.plusSeconds(NumberUtils.INTEGER_ONE));
        when(newPost.sourceLink()).thenReturn(sourcePost);

        var actual = redditLinkService.getNewLinks();

        assertNotNull(actual);
        assertEquals(expected, actual.toList());
    }
}
