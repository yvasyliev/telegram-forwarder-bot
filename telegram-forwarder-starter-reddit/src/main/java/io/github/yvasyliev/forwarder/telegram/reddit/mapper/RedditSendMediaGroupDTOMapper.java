package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataInputMediaDTOConverter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * {@link SendMediaGroupDTO} mapper.
 */
@Mapper(uses = RedditMetadataInputMediaDTOConverter.class)
public interface RedditSendMediaGroupDTOMapper {
    /**
     * Maps a list of {@link Link.Metadata} to {@link SendMediaGroupDTO}.
     *
     * @param medias     the list of Reddit link metadata
     * @param hasSpoiler whether the media has a spoiler
     * @param caption    the caption for the media group
     * @return the mapped {@link SendMediaGroupDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    SendMediaGroupDTO map(List<Link.Metadata> medias, @Context boolean hasSpoiler, String caption) throws IOException;
}
