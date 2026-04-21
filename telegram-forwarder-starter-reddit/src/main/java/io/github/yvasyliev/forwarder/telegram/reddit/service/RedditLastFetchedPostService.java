package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.service.LastFetchedPostService;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditLastFetchedPostMapper;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

/**
 * Service for managing the last fetched post information for Reddit subreddits.
 */
@RequiredArgsConstructor
public class RedditLastFetchedPostService {
    private final LastFetchedPostService lastFetchedPostService;
    private final RedditLastFetchedPostMapper redditLastFetchedPostMapper;

    /**
     * Retrieves the timestamp of the last published post for a given subreddit.
     *
     * @param subreddit the name of the subreddit for which to retrieve the last published post timestamp
     * @return the timestamp of the last published post for the specified subreddit
     */
    public Instant getLastPublishedAt(String subreddit) {
        return lastFetchedPostService.findPublishedAtById(redditLastFetchedPostMapper.map(subreddit));
    }

    /**
     * Saves the timestamp of the last published post for a given subreddit.
     *
     * @param subreddit   the name of the subreddit for which to save the last published post timestamp
     * @param publishedAt the timestamp of the last published post to be saved for the specified subreddit
     * @return the saved {@link LastFetchedPost} entity
     */
    public LastFetchedPost save(String subreddit, Instant publishedAt) {
        return lastFetchedPostService.save(redditLastFetchedPostMapper.map(subreddit, publishedAt));
    }
}
