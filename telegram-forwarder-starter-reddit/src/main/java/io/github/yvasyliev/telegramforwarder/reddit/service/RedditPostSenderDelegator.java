package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSenderResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Forwards Reddit posts using the appropriate post forwarder.
 */
@RequiredArgsConstructor
@Slf4j
public class RedditPostSenderDelegator {
    private final RedditPostSenderResolver redditPostSenderResolver;
    private final RedditInstantPropertyService instantPropertyService;

    /**
     * Forwards the given post.
     *
     * @param post the post to be forwarded
     */
    public void send(Link post) {
        try {
            redditPostSenderResolver.resolve(post).send(post);
        } catch (IOException | TelegramApiException e) {
            log.error("Failed to forward post: {}", post.permalink(), e);
        }

        instantPropertyService.saveLastCreated(post.created());
    }
}
