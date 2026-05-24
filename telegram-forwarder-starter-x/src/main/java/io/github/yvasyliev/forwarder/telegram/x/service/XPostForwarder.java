package io.github.yvasyliev.forwarder.telegram.x.service;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.service.PostForwarder;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Implementation of {@link PostForwarder} that receives new posts from multiple {@link FeedEntryMessageSource}
 * instances and forwards them to the {@link XPostSenderManager} for sending to Telegram.
 */
@RequiredArgsConstructor
public class XPostForwarder implements PostForwarder {
    private final List<FeedEntryMessageSource> messageSources;
    private final XPostSenderManager xPostSenderManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void forward() {
        messageSources.stream()
                .flatMap(this::receiveNewPosts)
                .forEach(xPostSenderManager::send);
    }

    private Stream<SyndEntry> receiveNewPosts(FeedEntryMessageSource source) {
        return Stream.generate(source::receive)
                .takeWhile(Objects::nonNull)
                .map(Message::getPayload);
    }
}
