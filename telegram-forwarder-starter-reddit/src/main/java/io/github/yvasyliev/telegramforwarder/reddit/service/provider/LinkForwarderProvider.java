package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;

import java.util.function.Function;

/**
 * A functional interface that provides a method to obtain a {@link LinkForwarder} based on a given {@link Link}.
 */
@FunctionalInterface
public interface LinkForwarderProvider extends Function<Link, LinkForwarder> {
}
