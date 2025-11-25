package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.MetadataInputMediaDTOConverter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Strategy for sending a group of media items as a single media group message.
 */
@RequiredArgsConstructor
public class MediaGroupMetadataSenderStrategy implements MetadataPartitionSenderStrategy {
    private final MetadataInputMediaDTOConverter metadataInputMediaDTOConverter;
    private final PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender;

    @Override
    public void send(
            List<Link.Metadata> metadataPartition,
            boolean hasSpoiler,
            String caption
    ) throws IOException, TelegramApiException {
        var inputMedias = metadataPartition.stream()
                .map(metadata -> metadataInputMediaDTOConverter.convert(metadata, hasSpoiler))
                .toList();

        mediaGroupSender.send(inputMedias, caption);
    }
}
