package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Manages the forwarding of Reddit posts based on their type and properties.
 * It retrieves new posts from a specified subreddit and forwards them using appropriate {@link Forwarder} instances.
 */
@RequiredArgsConstructor
@Slf4j
public class RedditPostForwarderManager implements PostForwarderManager {
    private final RedditInstantPropertyService instantPropertyService;
    private final RedditService redditService;
    private final RedditProperties redditProperties;
    private final ForwarderFactory forwarderFactory;

    @Override
    public void forward() {
        var lastCreated = instantPropertyService.getLastCreated();
        redditService.getSubredditNew(redditProperties.subreddit())
                .data()
                .children()
                .stream()
                .map(Thing::data)
                .filter(link -> link.created().isAfter(lastCreated))
                .sorted()
                .map(Link::sourceLink)
                .forEach(this::forwardPost);
    }

    private void forwardPost(Link link) {
        try {
            forwarderFactory.apply(link).forward(link);
        } catch (IOException | TelegramApiException e) {
            log.error("Failed to fetch post: {}", link.permalink(), e);
        }

        instantPropertyService.saveLastCreated(link.created());
    }
}
