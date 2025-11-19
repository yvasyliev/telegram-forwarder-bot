package io.github.yvasyliev.telegramforwarder.core.service;

/**
 * Service responsible for fetching posts from a source and forwarding them to a Telegram chat.
 */
@FunctionalInterface
public interface PostForwarder {
    /**
     * Fetches posts from a source and forwards them to a Telegram chat.
     */
    void forward();
}
