package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;

/**
 * Converts Reddit link metadata into Telegram input media DTOs.
 */
@RequiredArgsConstructor
public class MetadataInputMediaDTOConverter {
    private final MetadataPhotoUrlSelector photoUrlSelector;

    /**
     * Converts the given Reddit link metadata into an appropriate Telegram input media DTO.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the media has a spoiler
     * @return the converted Telegram input media DTO
     */
    public InputMediaDTO convert(Link.Metadata metadata, boolean hasSpoiler) {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> {
                var url = metadata.source().gif();
                var animation = InputFileDTO.fromUrl(url, hasSpoiler);
                yield InputMediaDTO.animation(animation);
            }

            case IMAGE -> {
                var url = photoUrlSelector.findBestPhotoUrl(metadata);
                var photo = InputFileDTO.fromUrl(url, hasSpoiler);
                yield InputMediaDTO.photo(photo);
            }
        };
    }
}
