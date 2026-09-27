package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.core.service.PostForwarder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Fetches new links from Reddit and forwards them to a Telegram chat.
 */
@Service
@RequiredArgsConstructor
public class RedditPostForwarder implements PostForwarder {
    private final RedditLinkService redditLinkService;
    private final RedditPostSenderManager redditPostSenderManager;

    @Override
    public void forward() {
        redditLinkService.getNewLinks().forEach(redditPostSenderManager::send);
    }
}
