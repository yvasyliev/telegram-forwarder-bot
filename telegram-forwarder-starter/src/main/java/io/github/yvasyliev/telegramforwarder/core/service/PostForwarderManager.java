package io.github.yvasyliev.telegramforwarder.core.service;

/**
 * Interface for managing post forwarding operations.
 * Implementations should define how posts are forwarded.
 */
@FunctionalInterface
public interface PostForwarderManager {
    /**
     * Initiates the forwarding of posts.
     * This method should contain the logic to forward posts as per the implementation.
     */
    void forward();
}
