package io.github.yvasyliev.telegramforwarderbot.reddit.service;

import io.github.yvasyliev.telegramforwarderbot.reddit.annotation.BoolInt;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Thing;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * Service interface for interacting with Reddit's API to fetch subreddit data.
 * This interface defines methods to retrieve new posts from a specified subreddit.
 */
public interface RedditService {
    /**
     * Retrieves the new posts from a specified subreddit.
     *
     * @param subreddit the name of the subreddit to fetch new posts from
     * @param rawJson   whether to return the response in raw JSON format
     * @return a {@link Thing} containing a {@link Listing} of new posts
     */
    @GetExchange("/r/{subreddit}/new")
    Thing<Listing> getSubredditNew(
            @PathVariable String subreddit,
            @RequestParam(value = "raw_json") @BoolInt boolean rawJson
    );
}
