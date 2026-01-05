package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaAnimationDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.UrlUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link InputMediaAnimationDTO} mapper.
 */
@Mapper(uses = UrlUtils.class)
public interface RedditInputMediaAnimationDTOMapper {
    /**
     * Maps Reddit link metadata to an {@link InputMediaAnimationDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the media has
     * @return the corresponding {@link InputMediaAnimationDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "mediaStream", source = "metadata.source.gif")
    @Mapping(target = "fileName", source = "metadata.source.gif")
    InputMediaAnimationDTO map(Link.Metadata metadata, boolean hasSpoiler) throws IOException;
}
