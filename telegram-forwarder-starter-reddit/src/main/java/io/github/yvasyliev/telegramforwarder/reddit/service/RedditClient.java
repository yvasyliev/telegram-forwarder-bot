package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.annotation.ClientRegistrationId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Client for interacting with the Reddit API.
 */
@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE)
@ClientRegistrationId(RedditClient.REDDIT_GROUP)
public interface RedditClient {
    /**
     * The client registration ID for Reddit API.
     */
    String REDDIT_GROUP = "reddit";

    /**
     * Retrieves the new posts from the specified subreddit.
     *
     * @param subreddit the name of the subreddit
     * @return a listing of new posts in the subreddit
     */
    @GetExchange("/r/{subreddit}/new?raw_json=1")
    Thing<Listing> getSubredditNew(@PathVariable String subreddit);
}
