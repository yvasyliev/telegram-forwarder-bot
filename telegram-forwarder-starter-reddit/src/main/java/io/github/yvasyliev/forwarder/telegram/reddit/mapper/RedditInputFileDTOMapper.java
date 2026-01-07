package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.RedditVideoDownloader;
import io.github.yvasyliev.forwarder.telegram.reddit.util.UrlUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.IOException;
import java.net.URL;

/**
 * {@link InputFileDTO} mapper.
 */
@Mapper(uses = {RedditVideoDownloader.class, UrlUtils.class})
public interface RedditInputFileDTOMapper {
    /**
     * Mapping method name for mapping file names.
     */
    String MAP_FILE_NAME = "mapFileName";

    /**
     * Maps a {@link URL} to an {@link InputFileDTO}.
     *
     * @param url the URL to map
     * @return the mapped {@link InputFileDTO}
     * @throws IOException if an I/O error occurs
     */
    @Mapping(target = "mediaStream", source = ".")
    @Mapping(target = "fileName", source = ".")
    InputFileDTO map(URL url) throws IOException;

    /**
     * Maps a {@link Link} to an {@link InputFileDTO}.
     *
     * @param post the Reddit link to map
     * @return the mapped {@link InputFileDTO}
     * @throws IOException if an I/O error occurs
     */
    @Mapping(target = "mediaStream", source = ".")
    @Mapping(target = "fileName", source = "id", qualifiedByName = MAP_FILE_NAME)
    InputFileDTO map(Link post) throws IOException;

    /**
     * Maps the file name for the given Reddit post ID.
     *
     * @param id the Reddit post ID
     * @return the mapped file name
     */
    @Named(MAP_FILE_NAME)
    default String mapFileName(String id) {
        return id + ".mp4";
    }
}
