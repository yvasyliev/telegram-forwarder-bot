package io.github.yvasyliev.forwarder.telegram.x.service.sender;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendPhotoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Strategy for sending X posts with a single photo to Telegram.
 */
@RequiredArgsConstructor
public class XPhotoPostSenderStrategy implements XPostSenderStrategy {
    private final XSendPhotoDTOMapper sendPhotoDTOMapper;
    private final PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender;

    /**
     * {@inheritDoc}
     *
     * @param post        {@inheritDoc}
     * @param description {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean canSend(SyndEntry post, XDescription description) {
        return NumberUtils.INTEGER_ONE.equals(description.images().size());
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
        photoSender.send(() -> sendPhotoDTOMapper.map(description));
    }
}
