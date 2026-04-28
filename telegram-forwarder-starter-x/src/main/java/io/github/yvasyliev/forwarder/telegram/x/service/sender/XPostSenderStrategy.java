package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;

/**
 * Strategy for sending X posts.
 */
public interface XPostSenderStrategy extends XPostSender {
    /**
     * Checks if the strategy can send the given X post based on its description.
     *
     * @param post        the X post to check
     * @param description the description of the X post to check
     * @return {@code true} if the strategy can send the post, {@code false} otherwise
     */
    boolean canSend(SyndEntry post, XDescription description);
}
