package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaVideoDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.util.UrlUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link InputMediaVideoDTO} mapper.
 */
@Mapper(uses = UrlUtils.class)
public interface RedditInputMediaVideoDTOMapper {
    /**
     * Maps Reddit link metadata to an {@link InputMediaVideoDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the media has a spoiler
     * @return the corresponding {@link InputMediaVideoDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "mediaStream", source = "metadata.source.mp4")
    @Mapping(target = "fileName", source = "metadata.source.mp4")
    InputMediaVideoDTO map(Link.Metadata metadata, boolean hasSpoiler) throws IOException;
}
