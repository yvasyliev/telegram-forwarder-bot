package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

/**
 * Service for interacting with Reddit links.
 */
@RequiredArgsConstructor
public class RedditLinkService {
    private final RedditInstantPropertyService instantPropertyService;
    private final RedditClient redditClient;
    private final RedditProperties redditProperties;

    /**
     * Retrieves new links from a specified subreddit that were created after the last recorded creation time.
     *
     * @return a stream of new links
     */
    public Stream<Link> getNewLinks() {
        var lastCreated = instantPropertyService.getLastCreated();
        return redditClient.getSubredditNew(redditProperties.subreddit())
                .data()
                .children()
                .stream()
                .map(Thing::data)
                .filter(link -> link.created().isAfter(lastCreated))
                .sorted()
                .map(Link::sourceLink);
    }
}
