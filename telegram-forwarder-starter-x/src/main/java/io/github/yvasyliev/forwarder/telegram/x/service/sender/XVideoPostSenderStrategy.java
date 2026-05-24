package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendVideoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Strategy for sending X posts as videos to Telegram.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class XVideoPostSenderStrategy implements XPostSenderStrategy {
    private final XSendVideoDTOMapper sendVideoDTOMapper;
    private final PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender;

    /**
     * {@inheritDoc}
     *
     * @param post        {@inheritDoc}
     * @param description {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean canSend(SyndEntry post, XDescription description) {
        return description.isVideo();
    }

    /**
     * {@inheritDoc}
     *
     * @param post        {@inheritDoc}
     * @param description {@inheritDoc}
     * @throws TelegramApiException {@inheritDoc}
     * @throws IOException          {@inheritDoc}
     */
    @Override
    public void send(SyndEntry post, XDescription description) throws TelegramApiException, IOException {
        videoSender.send(() -> sendVideoDTOMapper.map(post, description));
    }
}
