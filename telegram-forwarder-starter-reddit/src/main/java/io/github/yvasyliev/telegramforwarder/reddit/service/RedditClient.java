package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Client for interacting with the Reddit API.
 */
@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE)
public interface RedditClient {
    /**
     * Retrieves the new posts from the specified subreddit.
     *
     * @param subreddit the name of the subreddit
     * @return a listing of new posts in the subreddit
     */
    @GetExchange("/r/{subreddit}/new?raw_json=1")
    Thing<Listing> getSubredditNew(@PathVariable String subreddit);
}
