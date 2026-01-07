package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditSendMediaGroupDTOMapper;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Sender for Reddit metadata partitions that should be sent as media groups.
 */
@RequiredArgsConstructor
public class RedditMediaGroupMetadataSender implements RedditMetadataPartitionSender {
    private final RedditSendMediaGroupDTOMapper sendMediaGroupDTOMapper;
    private final PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> mediaGroupSender;

    @Override
    public void send(List<Link.Metadata> metadataPartition, boolean hasSpoiler, String caption)
            throws IOException, TelegramApiException {
        mediaGroupSender.send(() -> sendMediaGroupDTOMapper.map(metadataPartition, hasSpoiler, caption));
    }
}
