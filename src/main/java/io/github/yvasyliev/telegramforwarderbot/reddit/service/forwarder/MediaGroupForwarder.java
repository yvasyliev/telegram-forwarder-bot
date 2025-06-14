package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.util.PhotoUtils;
import io.github.yvasyliev.telegramforwarderbot.service.sender.MediaGroupSender;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MediaGroupForwarder implements Forwarder {
    private final TelegramProperties telegramProperties;
    private final MetadataForwarder animationMetadataForwarder;
    private final MetadataForwarder photoMetadataForwarder;
    private final MediaGroupSender mediaGroupSender;

    @Override
    public void forward(Link link) throws IOException, TelegramApiException {
        var hasSpoiler = link.isNsfw();
        var caption = link.title();
        var metadataList = link.galleryData()
                .items()
                .stream()
                .map(item -> link.mediaMetadata().get(item.mediaId()))
                .toList();
        var metadataPartitions = ListUtils.partition(metadataList, telegramProperties.mediaGroupMaxSize());

        for (var metadataPartition : metadataPartitions) {
            if (NumberUtils.INTEGER_ONE.equals(metadataPartition.size())) {
                var metadata = metadataPartition.getFirst();
                getMetadataForwarder(metadata).forward(metadata, hasSpoiler);
            } else {
                var inputMedias = metadataPartition.stream()
                        .map(metadata -> toInputMedia(metadata, hasSpoiler))
                        .toList();
                mediaGroupSender.sendMediaGroup(inputMedias, caption);
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
                var url = PhotoUtils.getUrl(metadata, telegramProperties.maxPhotoDimensions());
                var photo = InputFileDTO.fromUrl(url, hasSpoiler);
                yield InputMediaDTO.photo(photo);
            }
        };
    }
}
