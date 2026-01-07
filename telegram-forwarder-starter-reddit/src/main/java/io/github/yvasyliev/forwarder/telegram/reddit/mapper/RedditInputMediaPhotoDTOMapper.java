package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataPhotoUrlSelector;
import io.github.yvasyliev.forwarder.telegram.reddit.util.UrlUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link InputMediaPhotoDTO} mapper.
 */
@Mapper(uses = {RedditMetadataPhotoUrlSelector.class, UrlUtils.class})
public interface RedditInputMediaPhotoDTOMapper {
    /**
     * Maps Reddit link metadata to an {@link InputMediaPhotoDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the photo has a spoiler
     * @return the corresponding {@link InputMediaPhotoDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "mediaStream", source = "metadata")
    @Mapping(target = "fileName", source = "metadata")
    InputMediaPhotoDTO map(Link.Metadata metadata, boolean hasSpoiler) throws IOException;
}
