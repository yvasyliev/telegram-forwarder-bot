package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditLastFetchedPostMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditLastFetchedPostServiceTest {
    @InjectMocks private RedditLastFetchedPostService redditLastFetchedPostService;
    @Mock private LastFetchedPostService lastFetchedPostService;
    @Mock private RedditLastFetchedPostMapper redditLastFetchedPostMapper;

    @Test
    void testGetLastPublishedAt() {
        var subreddit = "subreddit";
        var id = mock(LastFetchedPostId.class);
        var expected = Instant.now();

        when(redditLastFetchedPostMapper.map(subreddit)).thenReturn(id);
        when(lastFetchedPostService.findPublishedAtById(id)).thenReturn(expected);

        var actual = redditLastFetchedPostService.getLastPublishedAt(subreddit);

        assertEquals(expected, actual);
    }

    @Test
    void testSave() {
        var subreddit = "subreddit";
        var publishedAt = Instant.now();
        var id = new LastFetchedPostId();

        id.setSourceType(LastFetchedPostId.SourceType.REDDIT);
        id.setSourceName(subreddit);

        var expected = new LastFetchedPost();

        expected.setId(id);
        expected.setPublishedAt(publishedAt);

        when(redditLastFetchedPostMapper.map(subreddit, publishedAt)).thenReturn(expected);
        when(lastFetchedPostService.save(expected)).thenReturn(expected);

        var actual = redditLastFetchedPostService.save(subreddit, publishedAt);

        assertEquals(expected, actual);
    }
}
