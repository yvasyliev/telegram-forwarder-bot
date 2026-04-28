package io.github.yvasyliev.forwarder.telegram.x.service;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPostSender;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.x.util.XDescriptionParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Manager for sending X posts to Telegram.
 */
@RequiredArgsConstructor
@Slf4j
public class XPostSenderManager {
    private static final XPostSender NOOP_SENDER = (post, _) -> log.warn(
            "No sender found for post: {}",
            post.getLink()
    );
    private final List<XPostSenderStrategy> postSenderStrategies;
    private final XDescriptionParser xDescriptionParser;

    /**
     * Sends the given X post to Telegram using the appropriate sender strategy based on its description.
     *
     * @param post the X post to send
     */
    public void send(SyndEntry post) {
        var description = xDescriptionParser.parse(post.getDescription().getValue());

        try {
            getPostSender(post, description).send(post, description);
        } catch (TelegramApiException | IOException e) {
            log.error("Failed to send post: {}", post.getLink(), e);
        }
    }

    private XPostSender getPostSender(SyndEntry post, XDescription description) {
        return postSenderStrategies.stream()
                .filter(postSenderStrategy -> postSenderStrategy.canSend(post, description))
                .findFirst()
                .map(XPostSender.class::cast)
                .orElse(NOOP_SENDER);
    }
}
