package io.github.yvasyliev.telegramforwarder.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.PhotoUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Forwards media groups from Reddit links to Telegram.
 * Handles both animated images and photos, forwarding them in groups
 * according to the specified maximum size in the Telegram properties.
 */
@RequiredArgsConstructor
public class MediaGroupForwarder implements Forwarder {
    private final TelegramMediaProperties mediaProperties;
    private final MetadataForwarder animationMetadataForwarder;
    private final MetadataForwarder photoMetadataForwarder;
    private final PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var hasSpoiler = link.isNsfw();
        var caption = link.title();
        var metadataList = link.galleryData()
                .items()
                .stream()
                .map(item -> link.mediaMetadata().get(item.mediaId()))
                .toList();
        var metadataPartitions = ListUtils.partition(metadataList, mediaProperties.mediaGroupMaxSize());

        for (var metadataPartition : metadataPartitions) {
            if (NumberUtils.INTEGER_ONE.equals(metadataPartition.size())) {
                var metadata = metadataPartition.getFirst();
                getMetadataForwarder(metadata).forward(metadata, hasSpoiler);
            } else {
                var inputMedias = metadataPartition.stream()
                        .map(metadata -> toInputMedia(metadata, hasSpoiler))
                        .toList();
                mediaGroupSender.send(inputMedias, caption);
            }
            caption = null;
        }
    }

    private MetadataForwarder getMetadataForwarder(Link.Metadata metadata) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> animationMetadataForwarder;
            case IMAGE -> photoMetadataForwarder;
        };
    }

    private InputMediaDTO toInputMedia(Link.Metadata metadata, boolean hasSpoiler) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> {
                var url = metadata.source().gif();
                var animation = InputFileDTO.fromUrl(url, hasSpoiler);
                yield InputMediaDTO.animation(animation);
            }

            case IMAGE -> {
                var url = PhotoUtils.getUrl(metadata, mediaProperties.maxPhotoDimensions());
                var photo = InputFileDTO.fromUrl(url, hasSpoiler);
                yield InputMediaDTO.photo(photo);
            }
        };
    }
}
