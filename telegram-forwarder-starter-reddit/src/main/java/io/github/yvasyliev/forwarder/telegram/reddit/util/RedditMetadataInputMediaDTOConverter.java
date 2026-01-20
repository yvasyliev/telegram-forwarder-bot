package io.github.yvasyliev.forwarder.telegram.reddit.util;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditInputMediaPhotoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.reddit.mapper.RedditInputMediaVideoDTOMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;

import java.io.IOException;

/**
 * Converts Reddit link metadata into Telegram input media DTOs.
 */
@RequiredArgsConstructor
public class RedditMetadataInputMediaDTOConverter {
    private final RedditInputMediaVideoDTOMapper inputMediaVideoDTOMapper;
    private final RedditInputMediaPhotoDTOMapper inputMediaPhotoDTOMapper;

    /**
     * Converts the given {@link Link.Metadata} into an {@link InputMediaDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the media has a spoiler
     * @return the corresponding {@link InputMediaDTO}
     * @throws IOException if an I/O error occurs during conversion
     */
    public InputMediaDTO convert(Link.Metadata metadata, @Context boolean hasSpoiler) throws IOException {
        return switch (metadata.type()) {
            case ANIMATED_IMAGE -> inputMediaVideoDTOMapper.map(metadata, hasSpoiler);
            case IMAGE -> inputMediaPhotoDTOMapper.map(metadata, hasSpoiler);
        };
    }
}
