package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.SendUrlDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
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
