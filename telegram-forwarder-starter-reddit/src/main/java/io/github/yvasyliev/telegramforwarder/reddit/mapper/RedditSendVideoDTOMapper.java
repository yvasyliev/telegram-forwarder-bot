package io.github.yvasyliev.telegramforwarder.reddit.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.SendVideoDTO;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendVideoDTO} mapper.
 */
@Mapper(uses = RedditInputFileDTOMapper.class)
public interface RedditSendVideoDTOMapper {
    /**
     * Maps a {@link Link} to a {@link SendVideoDTO}.
     *
     * @param post the Reddit post
     * @return the SendVideoDTO
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "video", source = ".")
    @Mapping(target = "caption", source = "title")
    @Mapping(target = "hasSpoiler", source = "nsfw")
    SendVideoDTO map(Link post) throws IOException;
}
