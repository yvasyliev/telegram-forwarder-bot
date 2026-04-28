package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Functional interface for sending X posts to Telegram.
 */
@FunctionalInterface
public interface XPostSender {
    /**
     * Sends the given X post to Telegram using the provided description.
     *
     * @param post        the X post to send
     * @param description the description of the X post to use for sending
     * @throws TelegramApiException if an error occurs while sending the post to Telegram
     * @throws IOException          if an I/O error occurs while sending the post to Telegram
     */
    void send(SyndEntry post, XDescription description) throws TelegramApiException, IOException;
}
