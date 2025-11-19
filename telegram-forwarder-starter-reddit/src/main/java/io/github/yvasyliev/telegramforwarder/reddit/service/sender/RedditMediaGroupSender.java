package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

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
 * Implementation of {@link RedditPostSender} for sending media group posts.
 */
@RequiredArgsConstructor
public class RedditMediaGroupSender implements RedditPostSender {
    private final TelegramMediaProperties mediaProperties;
    private final RedditMetadataSender redditAnimationMetadataSender;
    private final RedditMetadataSender redditPhotoMetadataSender;
    private final PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender;

    @Override
    public void send(Link post) throws IOException, TelegramApiException {
        var hasSpoiler = post.isNsfw();
        var caption = post.title();
        var metadataList = post.galleryData()
                .items()
                .stream()
                .map(item -> post.mediaMetadata().get(item.mediaId()))
                .toList();
        var metadataPartitions = ListUtils.partition(metadataList, mediaProperties.mediaGroupMaxSize());

        for (var metadataPartition : metadataPartitions) {
            if (NumberUtils.INTEGER_ONE.equals(metadataPartition.size())) {
                var metadata = metadataPartition.getFirst();
                getMetadataForwarder(metadata).send(metadata, hasSpoiler);
            } else {
                var inputMedias = metadataPartition.stream()
                        .map(metadata -> toInputMedia(metadata, hasSpoiler))
                        .toList();
                mediaGroupSender.send(inputMedias, caption);
            }
            caption = null;
        }
    }

    private RedditMetadataSender getMetadataForwarder(Link.Metadata metadata) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> redditAnimationMetadataSender;
            case IMAGE -> redditPhotoMetadataSender;
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
