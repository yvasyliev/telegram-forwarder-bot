package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * {@link SendUrlDTO} mapper.
 */
@Mapper
public interface RedditSendUrlDTOMapper {
    /**
     * Maps a {@link Link} to a {@link SendUrlDTO}.
     *
     * @param post the Reddit link
     * @return the mapped SendUrlDTO
     */
    @Mapping(target = "text", source = "title")
    SendUrlDTO map(Link post);
}
