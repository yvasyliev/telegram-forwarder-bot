package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.LinkForwarderProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Factory class for obtaining the appropriate {@link LinkForwarder} based on the properties of a given {@link Link}.
 * It selects the correct forwarder implementation depending on whether the link contains gallery data,
 * post hints, and other attributes.
 */
@RequiredArgsConstructor
@Slf4j
public class LinkForwarderFactory implements Function<Link, LinkForwarder> {
    private static final LinkForwarder NOOP_FORWARDER = link -> log.warn(
            "No forwarder found for link: {}",
            link.permalink()
    );
    private final List<LinkForwarderProvider> forwarderProviders;

    @Override
    public LinkForwarder apply(Link link) {
        return forwarderProviders.stream()
                .map(provider -> provider.apply(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(NOOP_FORWARDER);
    }
}
