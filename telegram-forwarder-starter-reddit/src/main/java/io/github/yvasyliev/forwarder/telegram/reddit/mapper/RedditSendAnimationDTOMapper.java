package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendAnimationDTO} mapper.
 */
@Mapper(uses = RedditInputFileDTOMapper.class)
public interface RedditSendAnimationDTOMapper {
    /**
     * Maps Reddit link metadata to a {@link SendAnimationDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the animation has a spoiler
     * @return the corresponding SendAnimationDTO
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "animation", source = "metadata.source.gif")
    SendAnimationDTO map(Link.Metadata metadata, boolean hasSpoiler) throws IOException;

    /**
     * Maps a Reddit link to a {@link SendAnimationDTO}.
     *
     * @param post the Reddit link
     * @return the corresponding SendAnimationDTO
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "animation", source = "preview.images.first.variants.mp4.source.url")
    @Mapping(target = "caption", source = "title")
    @Mapping(target = "hasSpoiler", source = "nsfw")
    SendAnimationDTO map(Link post) throws IOException;
}
