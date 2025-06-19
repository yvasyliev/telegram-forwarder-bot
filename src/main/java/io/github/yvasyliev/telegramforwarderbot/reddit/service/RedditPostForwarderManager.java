package io.github.yvasyliev.telegramforwarderbot.reddit.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Thing;
import io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarderbot.service.PostForwarderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Manages the forwarding of Reddit posts based on their type and properties.
 * It retrieves new posts from a specified subreddit and forwards them using appropriate {@link Forwarder} instances.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedditPostForwarderManager implements PostForwarderManager {
    private final RedditInstantPropertyService redditInstantPropertyService;
    private final RedditService redditService;
    private final RedditProperties redditProperties;
    private final Forwarder mediaGroupForwarder;
    private final Forwarder videoForwarder;
    private final Forwarder imageAnimationForwarder;
    private final Forwarder photoForwarder;
    private final Forwarder linkForwarder;
    private final Forwarder videoAnimationForwarder;

    @Override
    public void forward() {
        var lastCreated = redditInstantPropertyService.getLastCreated();
        redditService.getSubredditNew(redditProperties.subreddit(), true)
                .data()
                .children()
                .stream()
                .map(Thing::data)
                .filter(link -> link.created().isAfter(lastCreated))
                .sorted(Link::compareTo)
                .forEach(this::forwardPost);
    }

    private void forwardPost(Link link) {
        var sourceLink = ObjectUtils.defaultIfNull(CollectionUtils.lastElement(link.crosspostParentList()), link);
        var forwarder = getForwarder(sourceLink);

        if (forwarder != null) {
            try {
                forwarder.forward(sourceLink);
            } catch (IOException | TelegramApiException e) {
                log.error("Failed to fetch post: {}", link.permalink(), e);
            }
        } else {
            log.warn("No forwarder found for link: {}", sourceLink.permalink());
        }

        redditInstantPropertyService.saveLastCreated(link.created());
    }

    private Forwarder getForwarder(Link link) {
        if (link.hasGalleryData()) {
            return mediaGroupForwarder;
        }

        if (link.hasPostHint()) {
            return switch (link.postHint()) {
                case HOSTED_VIDEO -> videoForwarder;
                case IMAGE -> link.preview().images().getFirst().variants().hasGif()
                        ? imageAnimationForwarder
                        : photoForwarder;
                case LINK -> linkForwarder;
                case RICH_VIDEO -> {
                    var redditVideo = link.preview().redditVideoPreview();
                    yield redditVideo != null && redditVideo.isGif() ? videoAnimationForwarder : linkForwarder;
                }
                default -> {
                    log.warn("Unhandled post hint: {}", link.postHint());
                    yield null;
                }
            };
        }

        return null;
    }
}
