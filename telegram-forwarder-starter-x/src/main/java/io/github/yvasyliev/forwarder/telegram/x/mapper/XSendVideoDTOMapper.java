package io.github.yvasyliev.forwarder.telegram.x.mapper;

import com.rometools.rome.feed.synd.SyndEntry;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.util.InputFileDTOConverter;
import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import io.github.yvasyliev.forwarder.telegram.x.service.XVideoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

/**
 * {@link SendVideoDTO} mapper for X posts.
 */
@Mapper(uses = {XVideoService.class, InputFileDTOConverter.class})
public interface XSendVideoDTOMapper {
    /**
     * Maps an {@link SyndEntry} and an {@link XDescription} to a {@link SendVideoDTO}.
     *
     * @param post        the X post to map
     * @param description the X post description to map
     * @return the mapped {@link SendVideoDTO}
     * @throws IOException if an I/O error occurs during mapping
     */
    @Mapping(target = "video", source = "post.link")
    @Mapping(target = "caption", source = "description.title")
    SendVideoDTO map(SyndEntry post, XDescription description) throws IOException;
}
