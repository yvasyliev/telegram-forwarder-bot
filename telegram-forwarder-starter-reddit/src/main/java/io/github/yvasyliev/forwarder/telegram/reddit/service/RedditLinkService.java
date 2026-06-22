package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Thing;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.stream.Stream;

/**
 * Service for interacting with Reddit links.
 */
@RequiredArgsConstructor
public class RedditLinkService {
    private final RedditLastFetchedPostService redditLastFetchedPostService;
    private final RedditClient redditClient;
    private final String subreddit;

    /**
     * Retrieves new links from a specified subreddit that were created after the last recorded creation time.
     *
     * @return a stream of new links
     */
    public Stream<Link> getNewLinks() {
        return getNewLinks(redditLastFetchedPostService.getLastPublishedAt(subreddit));
    }

    private Stream<Link> getNewLinks(Instant lastPublishedAt) {
        return redditClient.getSubredditNew(subreddit)
                .data()
                .children()
                .stream()
                .map(Thing::data)
                .filter(link -> link.created().isAfter(lastPublishedAt))
                .sorted()
                .map(Link::sourceLink);
    }
}
