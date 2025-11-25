package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarder;
import lombok.RequiredArgsConstructor;

/**
 * Fetches new links from Reddit and forwards them to a Telegram chat.
 */
@RequiredArgsConstructor
public class RedditPostForwarder implements PostForwarder {
    private final RedditLinkService redditLinkService;
    private final RedditPostSenderDelegator redditPostSenderDelegator;

    @Override
    public void forward() {
        redditLinkService.getNewLinks().forEach(redditPostSenderDelegator::send);
    }
}
