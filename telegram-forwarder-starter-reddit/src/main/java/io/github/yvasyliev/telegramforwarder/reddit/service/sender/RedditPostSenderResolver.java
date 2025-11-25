package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * Resolves the appropriate {@link RedditPostSender} for a given {@link Link}.
 */
@RequiredArgsConstructor
@Slf4j
public class RedditPostSenderResolver {
    private static final RedditPostSender NOOP_FORWARDER = link -> log.warn(
            "No forwarder found for link: {}",
            link.permalink()
    );
    private final List<RedditPostSenderFactory> redditPostSenderFactories;

    /**
     * Returns the appropriate {@link RedditPostSender} for the given link.
     * If no suitable forwarder is found, returns a no-op forwarder that logs a warning.
     *
     * @param post the link to be forwarded
     * @return the appropriate {@link RedditPostSender}
     */
    public RedditPostSender resolve(Link post) {
        return redditPostSenderFactories.stream()
                .map(redditPostSenderFactory -> redditPostSenderFactory.getRedditPostSender(post))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(NOOP_FORWARDER);
    }
}
