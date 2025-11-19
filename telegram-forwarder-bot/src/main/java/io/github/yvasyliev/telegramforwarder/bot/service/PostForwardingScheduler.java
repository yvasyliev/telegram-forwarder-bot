package io.github.yvasyliev.telegramforwarder.bot.service;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scheduler that periodically forwards posts using registered {@link List} of {@link PostForwarder}s.
 * The frequency of forwarding can be configured via {@code scheduler.post-forward.fixed-delay} property defaulting
 * to 1 minute.
 */
@Service
@ConditionalOnProperty(name = "scheduler.post-forward.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class PostForwardingScheduler {
    private final List<PostForwarder> postForwarders;

    /**
     * Scheduled method that forwards posts using all registered {@link PostForwarder}s.
     * The frequency of execution is controlled by the {@code scheduler.post-forward.fixed-delay} property which
     * defaults to 1 minute.
     */
    @Scheduled(fixedDelayString = "${scheduler.post-forward.fixed-delay:1m}")
    public void forwardPosts() {
        postForwarders.forEach(postForwarder -> {
            try {
                postForwarder.forward();
            } catch (Exception e) {
                log.error("Failed to forward posts", e);
            }
        });
    }
}
