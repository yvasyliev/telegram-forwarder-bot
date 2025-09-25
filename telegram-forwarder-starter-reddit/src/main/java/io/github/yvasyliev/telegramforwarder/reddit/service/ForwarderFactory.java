package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.provider.ForwarderProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Factory class for obtaining the appropriate {@link Forwarder} based on the properties of a given {@link Link}.
 * It selects the correct forwarder implementation depending on whether the link contains gallery data,
 * post hints, and other attributes.
 */
@RequiredArgsConstructor
@Slf4j
public class ForwarderFactory implements Function<Link, Forwarder> {
    private static final Forwarder NOOP_FORWARDER = link -> log.warn(
            "No forwarder found for link: {}",
            link.permalink()
    );
    private final List<ForwarderProvider> forwarderProviders;

    @Override
    public Forwarder apply(Link link) {
        return forwarderProviders.stream()
                .map(provider -> provider.apply(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(NOOP_FORWARDER);
    }
}
