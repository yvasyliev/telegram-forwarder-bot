package io.github.yvasyliev.telegramforwarderbot.reddit.service;

import io.github.yvasyliev.telegramforwarderbot.reddit.annotation.BoolInt;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Thing;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface RedditService {
    @GetExchange("/r/{subreddit}/new")
    Thing<Listing> getSubredditNew(
            @PathVariable String subreddit,
            @RequestParam(value = "raw_json") @BoolInt boolean rawJson
    );
}
