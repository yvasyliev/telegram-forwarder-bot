package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.util.RedditMetadataPhotoUrlSelector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendPhotoDTO} mapper.
 */
@Mapper(uses = {RedditMetadataPhotoUrlSelector.class, RedditInputFileDTOMapper.class})
public interface RedditSendPhotoDTOMapper {
    /**
     * Maps Reddit link metadata to a {@link SendPhotoDTO}.
     *
     * @param metadata   the Reddit link metadata
     * @param hasSpoiler whether the photo has a spoiler
     * @return the corresponding SendPhotoDTO
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "photo", source = "metadata")
    SendPhotoDTO map(Link.Metadata metadata, boolean hasSpoiler) throws IOException;

    /**
     * Maps a Reddit link to a {@link SendPhotoDTO}.
     *
     * @param post the Reddit link
     * @return the corresponding SendPhotoDTO
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "photo", source = "preview.images.first.source.url")
    @Mapping(target = "caption", source = "title")
    @Mapping(target = "hasSpoiler", source = "nsfw")
    SendPhotoDTO map(Link post) throws IOException;
}
