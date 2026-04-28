package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendMediaGroupDTOMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for sending X posts as media groups when they contain multiple images.
 */
@RequiredArgsConstructor
public class XSendMediaGroupDTOStrategy implements XPostSenderStrategy {
    private final XSendMediaGroupDTOMapper sendMediaGroupDTOMapper;
    private final PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> mediaGroupSender;

    /**
     * {@inheritDoc}
     *
     * @param post        {@inheritDoc}
     * @param description {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean canSend(SyndEntry post, XDescription description) {
        return description.images().size() > NumberUtils.INTEGER_ONE;
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
        mediaGroupSender.send(() -> sendMediaGroupDTOMapper.map(description));
    }
}
