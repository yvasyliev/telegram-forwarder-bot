package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy.RedditPostSenderStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Manager for sending Reddit posts using appropriate senders.
 */
@RequiredArgsConstructor
@Slf4j
public class RedditPostSenderManager {
    private static final RedditPostSender NOOP_SENDER = link -> log.warn(
            "No sender found for post: {}",
            link.permalink()
    );
    private final List<RedditPostSenderStrategy> postSenderStrategies;
    private final RedditInstantPropertyService instantPropertyService;

    /**
     * Sends the given Reddit post using the appropriate sender strategy.
     *
     * @param post the Reddit post to send
     */
    public void send(Link post) {
        try {
            getSender(post).send(post);
        } catch (IOException | TelegramApiException e) {
            log.error("Failed to send post: {}", post.permalink(), e);
        }

        instantPropertyService.saveLastCreated(post.created());
    }

    private RedditPostSender getSender(Link post) {
        for (var postSenderStrategy : postSenderStrategies) {
            if (postSenderStrategy.canSend(post)) {
                return postSenderStrategy;
            }
        }

        return NOOP_SENDER;
    }
}
