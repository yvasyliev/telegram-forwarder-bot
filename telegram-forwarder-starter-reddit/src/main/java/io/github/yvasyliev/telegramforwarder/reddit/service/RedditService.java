package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Service interface for interacting with Reddit's API to fetch subreddit data.
 * This interface defines methods to retrieve new posts from a specified subreddit.
 */
@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE)
public interface RedditService {
    /**
     * Retrieves the new posts from a specified subreddit.
     *
     * @param subreddit the name of the subreddit to fetch new posts from
     * @return a {@link Thing} containing a {@link Listing} of new posts
     */
    @GetExchange("/r/{subreddit}/new?raw_json=1")
    Thing<Listing> getSubredditNew(@PathVariable String subreddit);
}
