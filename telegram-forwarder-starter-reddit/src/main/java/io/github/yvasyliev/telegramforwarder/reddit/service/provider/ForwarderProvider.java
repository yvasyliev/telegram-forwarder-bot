package io.github.yvasyliev.telegramforwarder.reddit.service.provider;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;

import java.util.function.Function;

/**
 * A functional interface that provides a method to obtain a {@link Forwarder} based on a given {@link Link}.
 */
@FunctionalInterface
public interface ForwarderProvider extends Function<Link, Forwarder> {
}
