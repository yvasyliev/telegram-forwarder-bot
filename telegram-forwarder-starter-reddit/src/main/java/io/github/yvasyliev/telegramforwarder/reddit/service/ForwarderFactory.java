package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for obtaining the appropriate {@link Forwarder} based on the properties of a given {@link Link}.
 * It selects the correct forwarder implementation depending on whether the link contains gallery data,
 * post hints, and other attributes.
 */
@RequiredArgsConstructor
@Slf4j
public class ForwarderFactory {
    private static final Forwarder NOOP_FORWARDER = link -> log.warn(
            "No forwarder found for link: {}",
            link.permalink()
    );
    private final Forwarder mediaGroupForwarder;
    private final Forwarder videoForwarder;
    private final Forwarder imageAnimationForwarder;
    private final Forwarder photoForwarder;
    private final Forwarder linkForwarder;
    private final Forwarder videoAnimationForwarder;

    /**
     * Returns the appropriate {@link Forwarder} for the given {@link Link}.
     *
     * @param link the link to evaluate
     * @return the corresponding forwarder, or a no-op forwarder if none matches
     */
    public Forwarder forLink(Link link) {
        if (link.hasGalleryData()) {
            return mediaGroupForwarder;
        }

        if (!link.hasPostHint()) {
            return NOOP_FORWARDER;
        }

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
                yield NOOP_FORWARDER;
            }
        };
    }
}
